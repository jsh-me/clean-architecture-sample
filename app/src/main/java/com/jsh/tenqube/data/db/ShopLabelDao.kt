package com.jsh.tenqube.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.jsh.tenqube.data.dto.LabelModel
import com.jsh.tenqube.data.dto.ShopModel

@Dao
interface ShopLabelDao{

/*    @Insert
    suspend fun insertShopLabel(shopLabel: LocalShopLabelModel)

    @Query("SELECT * FROM shopAndLabelList")
    suspend fun getShopAndLabels(): List<LocalShopLabelModel>

    @Query("DELETE FROM shopAndLabelList")
    suspend fun deleteAllShopAndLabels()

    @Query("SELECT count(*) FROM shopAndLabelList")
    suspend fun isShopAndLabelEmpty(): Int

//    @Transaction
//    @Query("SELECT * FROM shopList")
//    suspend fun getShopWithLabelList(): List<ShopLabelWithLabelList>

    @Transaction
    @Query("SELECT * FROM shopList")
    suspend fun getShopWithAllLabelList(): List<ShopWithAllLabelList>*/

//    @Transaction
//    suspend fun insertShopAndLabels(shop: ShopModel, labels: List<LabelModel>) {
//        val memoId = insertShop(shop)
//        for (i in labels.indices) {
//            labels[i].id = memoId
//            labels[i]. = i + 1
//        }
//        insertLabels(labels)
//    }
//

    @Query("SELECT * FROM shop")
    suspend fun getShop(): List<LocalShopAndLabels>
}

