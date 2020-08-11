package com.jsh.tenqube.data.shop

import com.jsh.tenqube.data.mapper.toDomainShopLabelList
import com.jsh.tenqube.domain.Result
import com.jsh.tenqube.domain.entity.DomainShop
import com.jsh.tenqube.domain.entity.DomainShopLabel.*
import com.jsh.tenqube.domain.repository.ShopRepository
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val remoteDataSource: ShopDataSource,
    private val localDataSource: ShopDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ShopRepository {

    //rivate var cachedShops: ConcurrentMap<String, DomainShop.Shop>? = null

    override suspend fun getShops(): Result<List<DomainShop.Shop>> = withContext(ioDispatcher) {
        if (localDataSource.isShopDBEmpty()) {
            Timber.e("remoteDataSource shop available")
            remoteDataSource.getShops().let { result ->
                if (result is Result.Success) {
                    cacheShop(result.data)

                    return@withContext Result.Success(result.data)
                } else Result.Error(Exception("Remote Illegal state"))
            }
        } else {
            Timber.e("localDataSource shop available")
            localDataSource.getShops().let { result ->
                if (result is Result.Success) {
                    return@withContext Result.Success(result.data)
                } else Result.Error(Exception("Local Illegal state"))
            }
        }
    }

//        return withContext(ioDispatcher){
//            if(!localDataSource.isShopDBEmpty()){
//                cachedShops?.let{ cachedTasks ->
//                    return@withContext Result.Success(cachedTasks.values.sortedBy { it.id })
//                }
//            }
//
//            val newShops = fetchShopsFromRemoteOrLocal()
//
//            (newShops as? Result.Success)?.let { refreshCache(it.data) }
//
//            cachedShops?.values?.let{ tasks ->
//                return@withContext Result.Success(tasks.sortedBy { it.id })
//            }
//
//            (newShops as? Result.Success)?.let {
//                if (it.data.isEmpty()) {
//                    return@withContext Result.Success(it.data)
//                }
//            }
//
//            return@withContext Result.Error(java.lang.Exception("Illegal state"))
//        }
  //  }

    //입출력이 존재할때 ioDIspatcher
    override suspend fun getShopDetails(): List<ShopLabel> = withContext(ioDispatcher) {
        localDataSource.getShopDetails().toDomainShopLabelList()
    }


    override suspend fun fetchShopFromRemoteOrLocal(id: String): Result<DomainShop.Shop> {
        val remoteShopData = remoteDataSource.getShop(id)

        when( remoteShopData ){
            is Result.Error -> Timber.w("Remote data source fetch failed")
            is Result.Success ->{
                refreshLocalDataSource(remoteShopData.data)
                return remoteShopData
            }
            else ->  throw IllegalStateException()
        }

        val localShopData = localDataSource.getShop(id)
        if( localShopData is Result.Success) return localShopData
        return Result.Error(Exception("Error fetching from remote and local"))
    }


    override suspend fun updateShop(shop: DomainShop.Shop) {
        coroutineScope {
            launch { localDataSource.insertShop(shop) }
            launch { remoteDataSource.insertShop(shop) }
        }

    }

    override suspend fun deleteShop(id: String) {
        coroutineScope {
            launch { localDataSource.deleteShop(id) }
            launch { remoteDataSource.deleteShop(id) }
        }
    }

    override suspend fun deleteAllShop() {
        coroutineScope {
            launch { localDataSource.deleteAllShop() }
            launch { remoteDataSource.deleteAllShop() }
        }
    }

    private suspend fun fetchShopsFromRemoteOrLocal(): Result<List<DomainShop.Shop>>{
        val remoteShopData = remoteDataSource.getShops()

        when( remoteShopData ){
            is Result.Error -> Timber.w("Remote data source fetch failed")
            is Result.Success ->{
                refreshLocalDataSource(remoteShopData.data)
                return remoteShopData
            }
            else ->  throw IllegalStateException()
        }

        val localShopData = localDataSource.getShops()
        if( localShopData is Result.Success) return localShopData
        return Result.Error(Exception("Error fetching from remote and local"))
    }

//    private fun refreshCache(shops: List<Shop>){
//        cachedShops?.clear()
//        shops.sortedBy { it.id }.forEach{
//            cacheAndPerform(it) {}
//        }
//    }

    private suspend fun refreshLocalDataSource(shopList: List<DomainShop.Shop>) {
        localDataSource.deleteAllShop()
        for (shop in shopList) {
            localDataSource.updateShop(shop)
        }
    }

    private suspend fun refreshLocalDataSource(shop: DomainShop.Shop) {
        localDataSource.updateShop(shop)
    }


    private suspend fun cacheShop(list: List<DomainShop.Shop>)= withContext(ioDispatcher) {
        list.map{
            //shop, shoplabel에 동시에 insert.
            localDataSource.insertShop(it)
        }
    }

   // private fun getShopWithId(id: String) = cachedShops?.get(id)

//    private fun cacheShop(shop: Shop): Shop {
//        val cachedShop = Shop(shop.id, shop.name, shop.imgUrl, shop.labelIds)
//
//        if(cachedShops == null){
//            cachedShops = ConcurrentHashMap()
//        }
//        cachedShops?.put(cachedShop.id, cachedShop)
//        return cachedShop
//    }

//    private inline fun cacheAndPerform(shop: Shop, perform: (Shop) -> Unit){
//        val cachedShop = cacheShop(shop)
//        perform(cachedShop)
//    }
}