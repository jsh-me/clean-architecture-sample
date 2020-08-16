package com.jsh.tenqube.data.source.shop

import com.jsh.tenqube.domain.util.Result
import com.jsh.tenqube.domain.entity.DomainShop.*

interface ShopDataSource {

    suspend fun getShops(): Result<List<Shop>>

    suspend fun getShop(id: String): Result<Shop>

    suspend fun insertShopLabels(shop: Shop)

    suspend fun deleteAllShopLabels()

    suspend fun insertShop(shop: Shop): Result<Unit>

    suspend fun updateShop(shop: Shop): Result<Unit>

    suspend fun deleteShop(id: String): Result<Unit>

    suspend fun deleteAllShop(): Result<Unit>

}