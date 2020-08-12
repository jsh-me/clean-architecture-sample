package com.jsh.tenqube.data.shop.remote

import com.jsh.tenqube.data.api.TenqubeService
import com.jsh.tenqube.data.shopAndLabel.ShopWithAllLabel
import com.jsh.tenqube.data.mapper.toDomainShopList
import com.jsh.tenqube.data.shop.ShopDataSource
import com.jsh.tenqube.domain.Result
import com.jsh.tenqube.domain.entity.DomainShop
import com.jsh.tenqube.domain.entity.DomainShop.Shop
import kotlinx.coroutines.*
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

    override suspend fun getShop(id: String): Result<Shop> = withContext(ioDispatcher) {
        return@withContext try{
            tenqubeServiceData?.let{ shopList->
                Result.Success(shopList[id])
            }
        } catch (e: Exception) {
            Result.Error(e)
        } as Result<Shop>
    }


    override suspend fun isShopDBEmpty(): Boolean = withContext(ioDispatcher) {
        return@withContext tenqubeServiceData?.size == 0
    }

    override suspend fun insertShop(shop: Shop) {
        coroutineScope {
            launch { tenqubeServiceData?.put(shop.id, shop) }
        }
    }

    override suspend fun updateShop(shop: Shop) {
        coroutineScope {
            launch { tenqubeServiceData?.set(shop.id, shop) }
        }
    }

    override suspend fun deleteShop(id: String) {
        coroutineScope {
            launch { tenqubeServiceData?.set(id, Shop("", "", "", emptyList())) }
        }
    }

    override suspend fun deleteAllShop() {
        coroutineScope {
            launch { tenqubeServiceData?.clear() }
        }
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