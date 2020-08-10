package com.jsh.tenqube.data.shop

import com.jsh.tenqube.data.db.LocalShopAndLabels
import com.jsh.tenqube.domain.Result
import com.jsh.tenqube.domain.entity.Shop
import com.jsh.tenqube.domain.repository.ShopRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val remoteDataSource: ShopDataSource,
    private val localDataSource: ShopDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ShopRepository {

    override suspend fun getShops(): Result<List<Shop>> = withContext(ioDispatcher) {
        if(localDataSource.isShopDBEmpty()){
            Timber.e("remoteDataSource shop available")
            remoteDataSource.getShops().let{ result->
                if(result is Result.Success){
                    cacheShop(result.data)

                    return@withContext Result.Success(result.data)
                } else Result.Error(Exception("Remote Illegal state"))
            }
        } else {
            Timber.e("localDataSource shop available")
            localDataSource.getShops().let{ result ->
                if(result is Result.Success){
                    return@withContext Result.Success(result.data)
                }else Result.Error(Exception("Local Illegal state"))
            }
        }
    }

    //local
//    override suspend fun getShopAndAllLabels(): List<ShopAndAllLabels>  = withContext(ioDispatcher) {
//        localDataSource.getShopAndAllLabels()
//    }

    override suspend fun getShop(id: String): Result<Shop> {
        throw UnsupportedOperationException("unsupported operation")
    }

    override suspend fun saveShop(shop: Shop) {
        throw UnsupportedOperationException("unsupported operation")
    }

    override suspend fun deleteShop(id: String) {
        throw UnsupportedOperationException("unsupported operation")
    }

    override suspend fun deleteAllShop() = withContext(ioDispatcher) {
        localDataSource.deleteAllShop()
    }

//    override suspend fun getShopWithRestList(): List<ShopLabelWithLabelList> = withContext(ioDispatcher) {
//        localDataSource.getShopWithRestList()
//    }

//    override suspend fun getShopWithAllLabelList(): List<ShopWithAllLabelList> = withContext(ioDispatcher) {
//        localDataSource.getShopWithAllLabelList()
//    }

    override suspend fun getShop(): List<LocalShopAndLabels> = withContext(ioDispatcher) {
        localDataSource.getShop()
    }

    private suspend fun fetchListFromRemoteOrLocal(){
        val remoteShopData = remoteDataSource.getShops()

        if (remoteShopData is Result.Success) {
            refreshLocalDataSource(remoteShopData.data)
        }
    }


    private suspend fun refreshLocalDataSource(shopList: List<Shop>) {
        for (shop in shopList) {
            localDataSource.saveShop(shop)
        }
    }
    private suspend fun cacheShop(list: List<Shop>)  = withContext(ioDispatcher) {
        list.map{
            //shop, shoplabel에 동시에 insert.
            localDataSource.insertShop(it)
        }
    }
}