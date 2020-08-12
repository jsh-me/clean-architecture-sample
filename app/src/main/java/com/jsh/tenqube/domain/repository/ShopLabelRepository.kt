package com.jsh.tenqube.domain.repository

import com.jsh.tenqube.domain.entity.DomainShop.*
import com.jsh.tenqube.domain.entity.DomainShopLabel.*

interface ShopLabelRepository{

    suspend fun getShopDetails(): List<ShopLabel>

    suspend fun insertShopLabels(shop: Shop)

    suspend fun deleteAllShopLabels()

    suspend fun updateShopLabels(shopLabel: SingleShopLabel)
}