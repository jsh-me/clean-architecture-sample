package com.jsh.tenqube.data.shop.remote

import com.jsh.tenqube.data.api.TenqubeService
import com.jsh.tenqube.data.db.LocalShopAndLabels
import com.jsh.tenqube.data.mapper.toDomainShopList
import com.jsh.tenqube.data.shop.ShopDataSource
import com.jsh.tenqube.domain.Result
import com.jsh.tenqube.domain.entity.Shop
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import javax.inject.Inject


class RemoteShopDataSource @Inject constructor(
    private val tenqubeService: TenqubeService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ShopDataSource {

    private var tenqubeServiceData: ConcurrentMap<String, Shop>?= null

    override suspend fun getShops(): Result<List<Shop>> = withContext(ioDispatcher) {
        return@withContext try{
            cacheShops((tenqubeService.getShops().results).toDomainShopList())
            Result.Success((tenqubeService.getShops().results).toDomainShopList())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

//    override suspend fun getShopAndAllLabels(): List<ShopAndAllLabels> {
//        throw UnsupportedOperationException("unsupported operation")
//    }

    override suspend fun getShop(id: String): Result<Shop> = withContext(ioDispatcher) {
        return@withContext try{
            tenqubeServiceData?.let{ shopList->
                Result.Success(shopList[id])
            }
        } catch (e: Exception) {
            Result.Error(e)
        } as Result<Shop>
    }

    override suspend fun isShopDBEmpty(): Boolean {
        throw UnsupportedOperationException("unsupported operation")
    }

    override suspend fun insertShop(shop: Shop){
        throw UnsupportedOperationException("unsupported operation")
    }

    override suspend fun saveShop(shop: Shop){
        throw UnsupportedOperationException("unsupported operation")
    }

    override suspend fun deleteShop(id: String) {
        throw UnsupportedOperationException("unsupported operation")
    }

    override suspend fun deleteAllShop() {
        throw UnsupportedOperationException("unsupported operation")
    }

//    override suspend fun getShopWithRestList(): List<ShopLabelWithLabelList> {
//        throw UnsupportedOperationException("unsupported operation")
//    }

//    override suspend fun getShopWithAllLabelList(): List<ShopWithAllLabelList> {
//        throw UnsupportedOperationException("unsupported operation")
//    }

    override suspend fun getShop(): List<LocalShopAndLabels> {
        throw UnsupportedOperationException("unsupported operation")
    }

    private fun cacheShops(results: List<Shop>){
        if(tenqubeServiceData == null){
            tenqubeServiceData = ConcurrentHashMap()
        }
        results.map{
            tenqubeServiceData?.put(it.id, it)
        }
    }

}