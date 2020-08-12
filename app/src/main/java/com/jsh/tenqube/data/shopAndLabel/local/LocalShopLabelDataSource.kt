package com.jsh.tenqube.data.shopAndLabel.local

import com.jsh.tenqube.data.db.TenqubeDatabase
import com.jsh.tenqube.data.mapper.toDataLocalShopLabelModel
import com.jsh.tenqube.data.mapper.toDataShopLabel
import com.jsh.tenqube.data.mapper.toDomainShopLabel
import com.jsh.tenqube.data.shopAndLabel.local.DataShopLocal.LocalShopLabelModel
import com.jsh.tenqube.data.shopAndLabel.ShopLabelDataSource
import com.jsh.tenqube.data.shopAndLabel.ShopWithAllLabel
import com.jsh.tenqube.domain.entity.DomainShop.Shop
import com.jsh.tenqube.domain.entity.DomainShopLabel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalShopLabelDataSource @Inject constructor(
    private val database: TenqubeDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ShopLabelDataSource {

    override suspend fun getShopDetails(): List<ShopWithAllLabel> = withContext(ioDispatcher) {
        database.shopLabelDao().getShopWithAllLabel()
    }

    override suspend fun insertShopLabels(shop: Shop) = withContext(ioDispatcher) {
        shop.labelIds.map{
            database.shopLabelDao().insertShopWithLabel(
                LocalShopLabelModel(shop.id, it)
            )
        }
    }

    override suspend fun deleteAllShopLabels() = withContext(ioDispatcher) {
        database.shopLabelDao().deleteAllShopLabel()
    }

    override suspend fun updateShopLabels(shopLabel: DomainShopLabel.SingleShopLabel) {
            database.shopLabelDao().updateShopLabels(shopLabel.toDataShopLabel())
    }
}
