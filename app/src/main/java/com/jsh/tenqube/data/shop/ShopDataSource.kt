package com.jsh.tenqube.data.shop

import com.jsh.tenqube.data.db.ShopAndAllLabels
import com.jsh.tenqube.domain.entity.Shop
import com.jsh.tenqube.domain.Result
import com.jsh.tenqube.domain.entity.Label

interface ShopDataSource {

    suspend fun getShops(): Result<List<Shop>>

    suspend fun getShopAndAllLabels(): List<ShopAndAllLabels>

    suspend fun isShopDBEmpty(): Boolean

    suspend fun getShop(id: String): Result<Shop>

    suspend fun insertShop(shop: Shop)

    suspend fun saveShop(shop: Shop)

    suspend fun deleteShop(id: String)

    suspend fun deleteAllShop()
}