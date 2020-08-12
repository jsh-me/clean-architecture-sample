package com.jsh.tenqube.data.shop

import com.jsh.tenqube.data.shopAndLabel.ShopWithAllLabel
import com.jsh.tenqube.domain.Result
import com.jsh.tenqube.domain.entity.DomainShop.*

interface ShopDataSource {

    suspend fun getShops(): Result<List<Shop>>

    suspend fun isShopDBEmpty(): Boolean

    suspend fun getShop(id: String): Result<Shop>

    suspend fun insertShop(shop: Shop): List<Unit>

    suspend fun updateShop(shop: Shop)

    suspend fun deleteShop(id: String)

    suspend fun deleteAllShop()

}