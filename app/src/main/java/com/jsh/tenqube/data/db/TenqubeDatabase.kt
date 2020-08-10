package com.jsh.tenqube.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jsh.tenqube.data.label.local.LabelDao
import com.jsh.tenqube.data.label.local.LocalLabelModel
import com.jsh.tenqube.data.shop.local.LocalShopModel
import com.jsh.tenqube.data.shop.local.ShopDao

@Database(entities = [LocalShopModel::class, LocalLabelModel::class], version = 1, exportSchema = false)
abstract class TenqubeDatabase: RoomDatabase() {

    abstract fun shopDao(): ShopDao

    abstract fun shopLabelDao(): ShopLabelDao

    abstract fun labelDao(): LabelDao

}