package com.jsh.tenqube.domain.repository

import com.jsh.tenqube.domain.entity.Shop
import com.jsh.tenqube.domain.util.Result

interface ShopRepository {

    suspend fun getShops(isUpdated: Boolean): Result<List<Shop>>

    suspend fun getShop(id: String, isUpdated: Boolean): Result<Shop>

    suspend fun updateShop(shop: Shop): Result<Unit>

    suspend fun insertShop(shop: Shop): Result<Unit>

    suspend fun deleteShop(id: String): Result<Unit>

    suspend fun deleteAllShop(): Result<Unit>

}