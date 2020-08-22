package com.jsh.practice.data.source.shop

import com.jsh.practice.domain.util.Result
import com.jsh.practice.domain.entity.Shop

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