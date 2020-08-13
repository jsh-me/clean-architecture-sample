package com.jsh.tenqube.data.source.shop.local

import com.jsh.tenqube.data.mapper.*
import com.jsh.tenqube.data.source.shop.ShopDataSource
import com.jsh.tenqube.data.db.TenqubeDatabase
import com.jsh.tenqube.data.source.shopAndLabel.local.DataShopLocal.*
import com.jsh.tenqube.domain.util.Result
import com.jsh.tenqube.domain.entity.DomainShop.*
import kotlinx.coroutines.*
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class LocalShopDataSource @Inject constructor(
    private val database: TenqubeDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ShopDataSource {

    override suspend fun getShops(): Result<List<Shop>> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(database.shopDao().getShops().toLocalDomainShopList())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getShop(id: String): Result<Shop> = withContext(ioDispatcher) {
        return@withContext try{
            Result.Success(database.shopDao().getShopById(id).toLocalDomainShop())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }


    override suspend fun updateShop(shop: Shop) = withContext(ioDispatcher){
        database.shopDao().updateShop(shop.toLocalDataShopModel())
    }


    override suspend fun deleteShop(id: String) = withContext(ioDispatcher){
        database.shopDao().deleteShopById(id)
    }

    override suspend fun deleteAllShop() = withContext(ioDispatcher) {
        database.shopDao().deleteAllShops()
        Timber.e("All Delete Completed")
    }

    //shop
    override suspend fun insertShop(shop: Shop) {
        coroutineScope {
            database.shopDao().insertShop(shop.toLocalDataShopModel())
            launch {
                shop.labelIds.map {
                    database.shopLabelDao().insertShopWithLabel(
                        LocalShopLabelModel(shop.id, it)) }
            }
        }
    }

    override suspend fun isShopDBEmpty(): Boolean = withContext(ioDispatcher) {
        return@withContext database.shopDao().isShopDBEmpty() == 0
    }
}