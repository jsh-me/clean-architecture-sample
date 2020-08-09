package com.jsh.tenqube.data.shop

import com.jsh.tenqube.data.label.LabelDataSource
import com.jsh.tenqube.domain.Result
import com.jsh.tenqube.domain.entity.Label
import com.jsh.tenqube.domain.entity.Shop
import com.jsh.tenqube.domain.repository.LabelRepository
import com.jsh.tenqube.domain.repository.ShopRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val remoteDataSource: ShopDataSource,
    private val localDataSource: ShopDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ShopRepository {
    private var cachedLabelList = mutableMapOf<String, Shop>()

    override suspend fun getShops(isUpdated: Boolean): Result<List<Shop>>
            = withContext(ioDispatcher) {
        remoteDataSource.getShops().let { result ->
            if (result is Result.Success) {
                result.data.map { shop ->
                    cacheList(shop)
                }
            }
            return@withContext result
        }

        //  val newShopsAndLabels = fetchListFromRemoteOrLocal(isUpdated)
    }

    override suspend fun getShop(id: String): Result<Shop> {
        TODO("Not yet implemented")
    }

    override suspend fun saveShop(shop: Shop) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteShop(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllShop() {
        TODO("Not yet implemented")
    }

    private suspend fun fetchListFromRemoteOrLocal(isUpdated: Boolean){
        val remoteShopData = remoteDataSource.getShops()

        if (remoteShopData is Result.Success) {
            refreshLocalDataSource(remoteShopData.data)
        }
    }


    private suspend fun refreshLocalDataSource(shopList: List<Shop>) {
        for (shop in shopList) {
            localDataSource.saveShop(shop)
        }
    }


    private fun cacheList(list: Any) {
        if (list is Shop) {
            cachedLabelList[list.id] = list
        }
    }
}