package com.jsh.tenqube.data.shopAndLabel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.jsh.tenqube.data.shopAndLabel.DataShopLocal.LocalShopLabelModel

@Dao
interface ShopLabelDao{
    @Transaction
    @Query("SELECT * FROM shop")
    fun getShopWithAllLabel(): List<ShopWithAllLabel>

    @Insert
    fun insertShopWithLabel(shopLabel: LocalShopLabelModel)

}