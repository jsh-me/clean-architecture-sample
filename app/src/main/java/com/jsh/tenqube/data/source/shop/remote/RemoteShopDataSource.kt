package com.jsh.tenqube.data.source.shop.remote

import com.jsh.tenqube.data.api.TenqubeService
import com.jsh.tenqube.data.mapper.toDomainShopList
import com.jsh.tenqube.data.source.shop.ShopDataSource
import com.jsh.tenqube.data.source.shop.local.ShopWithAllLabel
import com.jsh.tenqube.domain.entity.DomainLabel
import com.jsh.tenqube.domain.util.Result
import com.jsh.tenqube.domain.entity.DomainShop.Shop
import kotlinx.coroutines.*
import javax.inject.Inject


class RemoteShopDataSource @Inject constructor(
    private val tenqubeService: TenqubeService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ShopDataSource {

    override suspend fun getShops(): Result<List<Shop>> = withContext(ioDispatcher) {
        return@withContext try{
            Result.Success((tenqubeService.getShops().results).toDomainShopList())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

//    override suspend fun getShopDetails(): Result<List<ShopWithAllLabel>> {
//        return Result.Error(Exception("UnSupported Operation"))
//    }

    override suspend fun getShopDetails(): Result<List<Shop>> {
        return Result.Error(Exception("UnSupported Operation"))
    }

    override suspend fun insertShopLabels(shop: Shop) {
        Result.Error(Exception("UnSupported Operation"))
    }

    override suspend fun deleteAllShopLabels() {
        Result.Error(Exception("UnSupported Operation"))
    }

    override suspend fun getShop(id: String): Result<Shop> {
        return Result.Error(Exception("UnSupported Operation"))
    }

    override suspend fun insertShop(shop: Shop) {
        Result.Error(Exception("UnSupported Operation"))
    }

    override suspend fun updateShop(shop: Shop) {
        Result.Error(Exception("UnSupported Operation"))
    }

    override suspend fun deleteShop(id: String) {
        Result.Error(Exception("UnSupported Operation"))
    }

    override suspend fun deleteAllShop() {
        Result.Error(Exception("UnSupported Operation"))
    }

}