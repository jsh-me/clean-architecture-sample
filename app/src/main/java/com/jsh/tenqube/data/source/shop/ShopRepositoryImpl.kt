package com.jsh.tenqube.data.source.shop

import com.jsh.tenqube.data.source.shop.local.ShopWithAllLabel
import com.jsh.tenqube.domain.entity.DomainLabel
import com.jsh.tenqube.domain.entity.DomainLabel.*
import com.jsh.tenqube.domain.util.Result
import com.jsh.tenqube.domain.entity.DomainShop
import com.jsh.tenqube.domain.entity.DomainShop.Shop
import com.jsh.tenqube.domain.repository.ShopRepository
import com.jsh.tenqube.domain.util.Result.*
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
                    return@withContext Success(cachedShops.values.sortedBy { it.id })
                }
            }

            val newShops = fetchShops(isUpdated)
            (newShops as? Success)?.let { refreshCache(it.data) }

            cachedShops?.values?.let{ shops ->
                return@withContext Success(shops.sortedBy { it.id })
            }

            (newShops as? Success)?.let{
                if(it.data.isEmpty()){
                    return@withContext Success(it.data)
                }
            }
            return@withContext Error(Exception("Illegal State"))
        }
    }

    //shop과 label의 정보를 모두 가져온다.
    override suspend fun getShopDetails(): Result<List<Shop>> = withContext(ioDispatcher) {
        return@withContext localDataSource.getShopDetails()
    }

    private suspend fun fetchShops(id: String): Result<Shop> {
        val remoteShopData = remoteDataSource.getShop(id)

        when( remoteShopData ){
            is Error -> Timber.w("Remote data source fetch failed")
            is Success ->{
                refreshLocalDataSource(remoteShopData.data)
                return remoteShopData
            }
            else ->  throw IllegalStateException()
        }

        val localShopData = localDataSource.getShop(id)
        if( localShopData is Success) return localShopData
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
            }catch (e: Exception) {
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

    private suspend fun fetchShops(isUpdated: Boolean): Result<List<Shop>>  = withContext(ioDispatcher) {
        val remoteShopData = remoteDataSource.getShops()

        when( remoteShopData ){
            is Error -> Timber.w("Remote data source fetch failed")
            is Success -> {
                refreshLocalDataSource(remoteShopData.data)
                return@withContext remoteShopData
            }
            else ->  throw IllegalStateException()
        }

        if (isUpdated) {
            return@withContext Error(Exception("Can't force refresh: remote data source is unavailable"))
        }

        val localShopData = localDataSource.getShops()
        if( localShopData is Success) return@withContext localShopData
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