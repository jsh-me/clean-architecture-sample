package com.jsh.tenqube.domain.repository

import com.jsh.tenqube.domain.Result
import com.jsh.tenqube.domain.entity.DomainShop.*
import com.jsh.tenqube.domain.entity.DomainShopLabel.*

interface ShopRepository {

    suspend fun getShops(): Result<List<Shop>>

    suspend fun fetchShopFromRemoteOrLocal(id: String): Result<Shop>

    suspend fun updateShop(shop: Shop)

    suspend fun insertShop(shop: Shop)

    suspend fun deleteShop(id: String)

    suspend fun deleteAllShop()

}