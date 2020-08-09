package com.jsh.tenqube.data.shop.local

import com.jsh.tenqube.data.mapper.*
import com.jsh.tenqube.data.shop.ShopDataSource
import com.jsh.tenqube.data.db.TenqubeDatabase
import com.jsh.tenqube.domain.Result
import com.jsh.tenqube.domain.entity.Shop
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    override suspend fun saveShop(shop: Shop) = withContext(ioDispatcher){
        database.shopDao().saveShop(shop.toLocalDataShopModel())
    }


    override suspend fun deleteShop(id: String) = withContext(ioDispatcher){
        database.shopDao().deleteShopById(id)
    }

    override suspend fun deleteAllShop() = withContext(ioDispatcher) {
        database.shopDao().deleteAllShops()
        Timber.e("All Delete Completed")
    }
}