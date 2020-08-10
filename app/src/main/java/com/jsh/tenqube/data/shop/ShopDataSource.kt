package com.jsh.tenqube.data.shop

import com.jsh.tenqube.data.db.LocalShopAndLabels
import com.jsh.tenqube.domain.entity.Shop
import com.jsh.tenqube.domain.Result

interface ShopDataSource {

    suspend fun getShops(): Result<List<Shop>>

    suspend fun isShopDBEmpty(): Boolean

    suspend fun getShop(id: String): Result<Shop>

    suspend fun insertShop(shop: Shop)

    suspend fun saveShop(shop: Shop)

    suspend fun deleteShop(id: String)

    suspend fun deleteAllShop()

   // suspend fun getShopWithRestList(): List<ShopLabelWithLabelList>

  //  suspend fun getShopWithAllLabelList(): List<ShopWithAllLabelList>

    suspend fun getShop(): List<LocalShopAndLabels>


}