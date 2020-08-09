package com.jsh.tenqube.domain.repository

import com.jsh.tenqube.data.db.ShopAndAllLabels
import com.jsh.tenqube.domain.Result
import com.jsh.tenqube.domain.entity.Shop

interface ShopRepository {

    suspend fun getShops(): Result<List<Shop>>

    suspend fun getShopAndAllLabels(): List<ShopAndAllLabels>

    suspend fun getShop(id: String): Result<Shop>

    suspend fun saveShop(shop: Shop)

    suspend fun deleteShop(id: String)

    suspend fun deleteAllShop()
}