package com.jsh.practice.data.source.shop

import com.jsh.practice.domain.util.Result
import com.jsh.practice.domain.entity.Shop
import com.jsh.practice.domain.repository.ShopRepository
import com.jsh.practice.domain.util.Result.*
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val remoteDataSource: ShopDataSource,
    private val localDataSource: ShopDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ShopRepository {

    private var cachedShops: ConcurrentMap<String, Shop>?= null

    override suspend fun getShops(isUpdated: Boolean): Result<List<Shop>> {
        return withContext(ioDispatcher){
            if(!isUpdated) { //서버에서 데이터를 가져올 필요가 없다면 캐시에 있는 데이터를 리턴
                cachedShops?.let{ cachedShops ->
                    return@withContext Success(cachedShops.values.sortedBy { it.id }) //메모리 캐싱 리턴
                }
            }

            val newShops = fetchShops(isUpdated) //캐시가 없으면 여기까지 내려오니까, fetchshops에서는 로컬 먼저 불러오고, 로컬에도 없으면 리모트로.
            (newShops as? Success)?.let { refreshCache(it.data) } //newshop의 결과값을 다시 캐시로 넣음

            cachedShops?.values?.let { shops ->  //그럼 캐시가 생길테니 리턴함
                return@withContext Success(shops.sortedBy { it.id })
            }
            return@withContext Error(Exception("Illegal State"))
        }
    }

    override suspend fun getShop(id: String, isUpdated: Boolean): Result<Shop> {
        return withContext(ioDispatcher){
            if(!isUpdated) {
               getShopById(id)?.let {
                   return@withContext Success(it)
               }
            }
            val newShop = fetchShop(id, isUpdated)
            (newShop as? Success)?.let {
                cacheShop(it.data)
                return@withContext Success(getShopById(id)!!)
            }
            return@withContext Error(Exception("Error fetching from remote and local"))
        }
    }

    private suspend fun fetchShop(id: String, isUpdated: Boolean): Result<Shop> {
        val localShopData = localDataSource.getShop(id)
        if(localShopData is Success) return localShopData

        val remoteShopData = remoteDataSource.getShop(id)

        when(remoteShopData){
            is Error -> Timber.w("Remote data source fetch failed")
            is Success -> {
                refreshLocalDataSource(remoteShopData.data)
                return remoteShopData
            }
            else ->  throw IllegalStateException()
        }

        if (!isUpdated) {
            return Error(Exception("Refresh failed"))
        }

        return Error(Exception("Error fetching from remote and local"))
    }


    override suspend fun updateShop(shop: Shop): Result<Unit> {
       return coroutineScope {
            try {
                launch { localDataSource.updateShop(shop) }
                launch { remoteDataSource.updateShop(shop) }
                Success(Unit)
            } catch (e: Exception) {
                Error(e)
            }
        }
    }

    //insert시 LocalShop과 LocalLabel 에 값이 들어가게된다.
    override suspend fun insertShop(shop: Shop): Result<Unit> = withContext(ioDispatcher) {
        return@withContext coroutineScope {
            try {
                launch {
                    localDataSource.insertShop(shop)
                    localDataSource.insertShopLabels(shop)
                } //Shop ID만 LocalShopLabel 에 저장
                launch {
                    remoteDataSource.insertShop(shop)
                    remoteDataSource.insertShopLabels(shop)
                }
                cachedShops?.put(shop.id, shop)
                Success(Unit)
            } catch (e: Exception) {
                Error(e)
            }
        }
    }

    override suspend fun deleteShop(id: String): Result<Unit> = withContext(ioDispatcher) {
        return@withContext coroutineScope {
            try {
                launch { localDataSource.deleteShop(id) }
                launch { remoteDataSource.deleteShop(id) }
                cachedShops?.remove(id)
                Success(Unit)
            } catch (e: Exception) {
                Error(e)
            }
        }
    }

    //shop과 ShopLabel 값을 전부 삭제한다.
    override suspend fun deleteAllShop(): Result<Unit> = withContext(ioDispatcher) {
        return@withContext coroutineScope {
            try {
                launch {
                    localDataSource.deleteAllShop()
                    localDataSource.deleteAllShopLabels()
                }
                launch {
                    remoteDataSource.deleteAllShop()
                    remoteDataSource.deleteAllShopLabels()
                }
                cachedShops?.clear()
                Success(Unit)
            } catch (e: Exception) {
                Error(e)
            }
        }
    }

    private suspend fun fetchShops(isUpdated: Boolean): Result<List<Shop>> = withContext(ioDispatcher) {
        val localShopData = localDataSource.getShops()

        (localShopData as? Success)?.let {
            if(it.data.isNotEmpty())
            return@withContext localShopData //로컬에 데이터가 있으면 로컬 데이터를 반환
        }

        val remoteShopData = remoteDataSource.getShops() //리모트 데이터 가져옴.

        when( remoteShopData ){
            is Error -> Timber.w("Remote data source fetch failed")
            is Success -> {
                refreshLocalDataSource(remoteShopData.data) //local에 remote 데이터를 넣음
                return@withContext remoteShopData
            }
            else ->  throw IllegalStateException()
        }

        if (isUpdated) {
            return@withContext Error(Exception("Can't force refresh: remote data source is unavailable"))
        }

        return@withContext Error(Exception("Error fetching from remote and local"))
    }

    private fun refreshCache(shops: List<Shop>){
        cachedShops?.clear()
        shops.sortedBy { it.id }.forEach {
            cacheAndPerform(it) {}
        }
    }

    private suspend fun refreshLocalDataSource(shopList: List<Shop>) = withContext(ioDispatcher) {
        localDataSource.deleteAllShop()
        for (shop in shopList) {
            localDataSource.insertShop(shop)
            localDataSource.insertShopLabels(shop)
        }
    }

    private fun getShopById(id: String) = cachedShops?.get(id)

    private suspend fun refreshLocalDataSource(shop: Shop) = withContext(ioDispatcher){
        localDataSource.insertShop(shop)
        localDataSource.insertShopLabels(shop)
    }

    private fun cacheShop(shop: Shop): Shop {
       val cachedShop = Shop(shop.id, shop.name, shop.imgUrl, shop.labels)
        if(cachedShops == null){
            cachedShops = ConcurrentHashMap()
        }
        cachedShops?.put(cachedShop.id, cachedShop)
        return cachedShop
    }

    private inline fun cacheAndPerform(shop: Shop, perform: (Shop) -> Unit) {
        val cachedShop = cacheShop(shop)
        perform(cachedShop)
    }
}