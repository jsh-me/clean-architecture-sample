package com.jsh.tenqube.domain.repository

import com.jsh.tenqube.data.source.shop.local.ShopWithAllLabel
import com.jsh.tenqube.domain.entity.DomainLabel
import com.jsh.tenqube.domain.entity.DomainLabel.Label
import com.jsh.tenqube.domain.util.Result
import com.jsh.tenqube.domain.entity.DomainShop.*

interface ShopRepository {

    suspend fun getShops(isUpdated: Boolean): Result<List<Shop>>

    suspend fun getShopDetails(): Result<List<Shop>>

    //suspend fun updateShopLabels(shopLabel: SingleShopLabel)

    //suspend fun fetchShopFromRemoteOrLocal(id: String): Result<Shop>

    suspend fun updateShop(shop: Shop)

    suspend fun insertShop(shop: Shop)

    suspend fun deleteShop(id: String)

    suspend fun deleteAllShop()

}