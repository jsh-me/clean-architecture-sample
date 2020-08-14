package com.jsh.tenqube.data.source.shop.local

import com.jsh.tenqube.data.mapper.*
import com.jsh.tenqube.data.source.shop.ShopDataSource
import com.jsh.tenqube.data.source.shop.local.DataShopLabel.*
import com.jsh.tenqube.domain.entity.DomainLabel
import com.jsh.tenqube.domain.entity.DomainLabel.*
import com.jsh.tenqube.domain.util.Result
import com.jsh.tenqube.domain.entity.DomainShop.*
import com.jsh.tenqube.domain.util.Result.*
import kotlinx.coroutines.*
import timber.log.Timber
import java.lang.Exception
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

class LocalShopDataSource @Inject constructor(
    private val shopDao: ShopDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ShopDataSource {

    override suspend fun getShops(): Result<List<Shop>> = withContext(ioDispatcher) {
        return@withContext try {
            Success(shopDao.getShops().toLocalDomainShopList())
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getShop(id: String): Result<Shop> = withContext(ioDispatcher) {
        return@withContext try{
            Success(shopDao.getShopById(id).toLocalDomainShop())
        } catch (e: Exception) {
            Error(e)
        }
    }

//    override suspend fun getShopDetails(): Result<List<ShopWithAllLabel>> = withContext(ioDispatcher) {
//        return@withContext try {
//                Result.Success(shopDao.getShopWithAllLabel())
//            } catch (e: Exception) {
//                Result.Error(e)
//            }
//    }

    override suspend fun getShopDetails(): Result<List<Shop>> = withContext(ioDispatcher) {
        shopDao.getShopWithAllLabel().let {
            return@withContext Success(it.map {
                Shop(
                    it.shop.id,
                    it.shop.shopName,
                    it.shop.shopUrl,
                    it.shopLabels.toLocalDomainLabelList())
            })
        }
    }

    override suspend fun insertShopLabels(shop: Shop) {
        coroutineScope {
            launch { shop.labels.map {
                shopDao.insertShopWithLabel(LocalShopLabelModel(shop.id, it.id)) }
            }
        }
    }

    override suspend fun deleteAllShopLabels() {
        shopDao.deleteAllShopLabel()
    }

    override suspend fun updateShop(shop: Shop) = withContext(ioDispatcher){
        shopDao.updateShop(shop.toLocalDataShopModel())
    }


    override suspend fun deleteShop(id: String) = withContext(ioDispatcher){
        shopDao.deleteShopById(id)
    }

    override suspend fun deleteAllShop() = withContext(ioDispatcher) {
        shopDao.deleteAllShops()
        Timber.e("All Delete Completed")
    }

    //shop
    override suspend fun insertShop(shop: Shop) {
        coroutineScope {
            shopDao.insertShop(shop.toLocalDataShopModel())
//            launch {
//                shop.labelIds.map {
//                    database.shopLabelDao().insertShopWithLabel(
//                        LocalShopLabelModel(shop.id, it)) }
//            }
        }
    }

}