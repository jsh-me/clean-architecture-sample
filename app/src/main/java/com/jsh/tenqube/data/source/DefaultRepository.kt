package com.jsh.tenqube.data.source

import com.jsh.tenqube.data.shop.ShopDataSource
import com.jsh.tenqube.domain.Result
import com.jsh.tenqube.domain.Result.*
import com.jsh.tenqube.domain.entity.Label
import com.jsh.tenqube.domain.entity.Shop
import com.jsh.tenqube.domain.repository.LabelRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultRepository
//@Inject constructor(
//    private val remoteDataSource: ShopDataSource,
//    private val localDataSource: ShopDataSource,
//    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
//): LabelRepository{
//    private var cachedlabelList = mutableMapOf<String, String>()
//    private var cachedShopList = mutableMapOf<String, Shop>()
//
//    override suspend fun getLabels(isUpdated: Boolean): Result<MutableMap<String, String>> = withContext(ioDispatcher) {
//                remoteDataSource.getLabels().let { result ->
//                    if (result is Success) {
//                        result.data.map { label ->
//                            cacheList(label)
//                        }
//                    }
//                    return@withContext Success(cachedlabelList)
//                }
//
//      //  val newShopsAndLabels = fetchListFromRemoteOrLocal(isUpdated)
//    }
//
//    private suspend fun fetchListFromRemoteOrLocal(isUpdated: Boolean): Result<List<Shop>> {
//        val remoteShopData = remoteDataSource.getShops()
//        val remoteLabelData = remoteDataSource.getLabels()
//
//       if (remoteShopData is Success && remoteLabelData is Success) {
//           refreshLocalDataSource(remoteShopData.data, remoteLabelData.data)
//       }
//    }
//
//    override suspend fun getShops(isUpdated: Boolean): Result<List<Shop>> = withContext(ioDispatcher){
//        return@withContext if(!isUpdated) {
//            remoteDataSource.getShops()
//        } else {
//            localDataSource.getShops()
//        }
//    }
//
//    override suspend fun getShop(id: String): Result<Shop> {
//        TODO("Not yet implemented")
//    }
//
//    private fun mapToLabelAndShop(shopList: List<Shop>, labelList: List<Label>){
//        shopList.map{shop ->
//
//        }
//    }
//
//    private suspend fun refreshLocalDataSource(shopList: List<Shop>, labelList: List<Label>) {
//        localDataSource.deleteAllShop()
//        for(shop in shopList) {
//            localDataSource.saveShop(shop)
//        }
//        for(label in labelList){
//            localDataSource.saveLabel(label)
//        }
//    }
//
//
//    private fun cacheList(list: Any) {
//        if(list is Label){
//            cachedlabelList[list.id] = list.name
//        } else if (list is Shop){
//            cachedShopList[list.id] = list
//        }
//    }
//}