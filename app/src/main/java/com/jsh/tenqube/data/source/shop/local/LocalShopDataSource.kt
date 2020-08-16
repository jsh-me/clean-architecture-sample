package com.jsh.tenqube.data.source.shop.local

import com.jsh.tenqube.data.mapper.*
import com.jsh.tenqube.data.source.shop.ShopDataSource
import com.jsh.tenqube.domain.util.Result
import com.jsh.tenqube.domain.entity.Shop
import com.jsh.tenqube.domain.util.Result.*
import kotlinx.coroutines.*
import timber.log.Timber
import java.lang.Exception
import java.util.*
import javax.inject.Inject

class LocalShopDataSource @Inject constructor(
    private val shopDao: ShopDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ShopDataSource {

    override suspend fun getShops(): Result<List<Shop>> = withContext(ioDispatcher) {
        return@withContext try {
            shopDao.getShopWithAllLabel().let {
                return@withContext Success(it.map {
                    Shop(
                        it.shop.id,
                        it.shop.shopName,
                        it.shop.shopUrl,
                        it.shopLabels.toLocalDomainLabelList()
                    )
                })
            }
        } catch (e: EmptyStackException) {
            Error(e)
        }
    }

    override suspend fun getShop(id: String): Result<Shop> = withContext(ioDispatcher) {
        return@withContext try {
            Success(shopDao.getShopById(id).toLocalDomainShop())
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun insertShopLabels(shop: Shop) {
        coroutineScope {
            launch {
                shop.labels.map {
                    shopDao.insertShopWithLabel(LocalShopLabelModel(shop.id, it.id))
                }
            }
        }
    }

    override suspend fun deleteAllShopLabels() {
        shopDao.deleteAllShopLabel()
    }

    override suspend fun updateShop(shop: Shop): Result<Unit> = withContext(ioDispatcher) {
       return@withContext try {
           Success(shopDao.updateShop(shop.toLocalDataShopModel()))
       }catch (e: Exception) {
           Error(e)
       }
    }

    override suspend fun deleteShop(id: String): Result<Unit> = withContext(ioDispatcher) {
        return@withContext try {
            Success(shopDao.deleteShopById(id))
        }catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun deleteAllShop(): Result<Unit> = withContext(ioDispatcher) {
        return@withContext try {
            Timber.e("All Delete Completed")
            Success(shopDao.deleteAllShops())
        }catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun insertShop(shop: Shop): Result<Unit> = withContext(ioDispatcher) {
        return@withContext try {
            Success(shopDao.insertShop(shop.toLocalDataShopModel()))
        } catch (e: Exception) {
            Error(e)
        }
    }

}