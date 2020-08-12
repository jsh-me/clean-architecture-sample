package com.jsh.tenqube.data.shopAndLabel

import com.jsh.tenqube.domain.entity.DomainShop.Shop
import com.jsh.tenqube.domain.entity.DomainShopLabel
import com.jsh.tenqube.domain.entity.DomainShopLabel.*

interface ShopLabelDataSource {

    suspend fun getShopDetails(): List<ShopWithAllLabel>

    suspend fun insertShopLabels(shop: Shop): List<Unit>

    suspend fun deleteAllShopLabels()

    suspend fun updateShopLabels(shopLabel: SingleShopLabel)
}