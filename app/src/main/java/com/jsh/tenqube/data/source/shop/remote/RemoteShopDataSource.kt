package com.jsh.tenqube.data.source.shop.remote

import com.jsh.tenqube.data.api.TenqubeService
import com.jsh.tenqube.data.source.label.local.LocalLabelDataSource
import com.jsh.tenqube.data.source.shop.ShopDataSource
import com.jsh.tenqube.domain.entity.DomainLabel.*
import com.jsh.tenqube.domain.util.Result
import com.jsh.tenqube.domain.entity.DomainShop.Shop
import kotlinx.coroutines.*
import javax.inject.Inject


class RemoteShopDataSource @Inject constructor(
    private val tenqubeService: TenqubeService,
    private val labelDataSource: LocalLabelDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ShopDataSource {

    override suspend fun getShops(): Result<List<Shop>> = withContext(ioDispatcher) {
        return@withContext try{
            val labelList = mutableMapOf<String, String>()

            labelDataSource.getLabels().let{
                if( it is Result.Success){
                    it.data.map{
                        labelList.put(it.id, it.name)
                    }
                }
            }

            Result.Success((tenqubeService.getShops().results.map{
                Shop(id = it.id, name = it.name, imgUrl = it.imgUrl, labels = it.labelIds.map { labelId ->
                    Label( id = labelId, name = labelList[labelId]!!) }) //List<Label>
                 }))

        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getShopDetails(): Result<List<Shop>> {
        return Result.Error(Exception("UnSupported Operation"))
    }

    override suspend fun insertShopLabels(shop: Shop) {
        Result.Error(Exception("UnSupported Operation"))
    }

    override suspend fun deleteAllShopLabels() {
        Result.Error(Exception("UnSupported Operation"))
    }

    override suspend fun getShop(id: String): Result<Shop> {
        return Result.Error(Exception("UnSupported Operation"))
    }

    override suspend fun insertShop(shop: Shop): Result<Unit> {
        return Result.Error(Exception("UnSupported Operation"))
    }

    override suspend fun updateShop(shop: Shop): Result<Unit> {
        return Result.Error(Exception("UnSupported Operation"))
    }

    override suspend fun deleteShop(id: String): Result<Unit> {
        return Result.Error(Exception("UnSupported Operation"))
    }

    override suspend fun deleteAllShop(): Result<Unit> {
        return Result.Error(Exception("UnSupported Operation"))
    }

}