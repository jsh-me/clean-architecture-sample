package com.jsh.tenqube.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jsh.tenqube.data.label.local.DataLabel
import com.jsh.tenqube.data.label.local.DataLabel.*
import com.jsh.tenqube.data.label.local.LabelDao
import com.jsh.tenqube.data.shop.local.DataShop
import com.jsh.tenqube.data.shop.local.DataShop.*
import com.jsh.tenqube.data.shop.local.ShopDao
import com.jsh.tenqube.data.shopAndLabel.DataShopLocal
import com.jsh.tenqube.data.shopAndLabel.DataShopLocal.*
import com.jsh.tenqube.data.shopAndLabel.ShopLabelDao

@Database(entities = [LocalShopModel::class, LocalLabelModel::class, LocalShopLabelModel::class], version = 1, exportSchema = false)
abstract class TenqubeDatabase: RoomDatabase() {

    abstract fun shopDao(): ShopDao

    abstract fun shopLabelDao(): ShopLabelDao

    abstract fun labelDao(): LabelDao

}