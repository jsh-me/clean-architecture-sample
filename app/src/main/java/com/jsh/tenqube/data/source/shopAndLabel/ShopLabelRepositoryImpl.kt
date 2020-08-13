package com.jsh.tenqube.data.source.shopAndLabel

import com.jsh.tenqube.data.mapper.toDomainShopLabelList
import com.jsh.tenqube.domain.entity.DomainShop.*
import com.jsh.tenqube.domain.entity.DomainShopLabel.*
import com.jsh.tenqube.domain.repository.ShopLabelRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShopLabelRepositoryImpl @Inject constructor(
    private val localDataSource: ShopLabelDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ShopLabelRepository {

    //입출력이 존재할때 ioDIspatcher
    override suspend fun getShopDetails(): List<ShopLabel> = withContext(ioDispatcher) {
        localDataSource.getShopDetails().toDomainShopLabelList()
    }

    override suspend fun insertShopLabels(shop: Shop) {
        localDataSource.insertShopLabels(shop)
    }

    override suspend fun deleteAllShopLabels() = withContext(ioDispatcher) {
        localDataSource.deleteAllShopLabels()
    }

    override suspend fun updateShopLabels(shopLabel: SingleShopLabel) = withContext(ioDispatcher){
        localDataSource.updateShopLabels(shopLabel)
    }
}