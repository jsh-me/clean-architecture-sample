package com.jsh.tenqube.presentation.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsh.tenqube.data.mapper.toLocalDomainLabel
import com.jsh.tenqube.data.mapper.toLocalDomainLabelList
import com.jsh.tenqube.data.mapper.toLocalDomainShop
import com.jsh.tenqube.domain.Result
import com.jsh.tenqube.domain.entity.Shop
import com.jsh.tenqube.domain.entity.Label
import com.jsh.tenqube.domain.usecase.*
import kotlinx.coroutines.*

class MainViewModel  @ViewModelInject constructor(
    private val getShopsUseCase: GetShopsUseCase,
    private val getLabelsUseCase: GetLabelsUseCase,
    private val deleteAllShopUseCase: DeleteAllShopUseCase,
    private val getShopWithLabelsUseCase: GetShopWithLabelsUseCase
): ViewModel() {

    var shopList = MutableLiveData<List<Shop>>()
  //  var labelList = MutableLiveData<Map<String, String>>() // label id, label name
    var labelList = MutableLiveData<List<List<Label>>>()

    init {
        //      deleteAll()
//        loadAllLabels()
//        loadAllShops()

        //모든 데이터를 불러온다.

        initData()
        loadData()
    }

    private fun deleteAll() = viewModelScope.launch {
        deleteAllShopUseCase()
    }

    private fun initData() = viewModelScope.launch {
        getLabelsUseCase()
        getShopsUseCase()
    }


    private fun loadData() = viewModelScope.launch {
        //val deferred = async {
//        getLabelsUseCase().let {
//            if (it is Result.Success) labelList.value = it.data
//        }
//        //}
//        //deferred.await()
//
//        getShopsUseCase().let { result ->
//            if (result is Result.Success) shopList.value = result.data
//        }
//        Timber.e("first viewmodel end!")

        labelList.value = getShopWithLabelsUseCase().map{
            it.labels.toLocalDomainLabelList()
        }

        shopList.value = getShopWithLabelsUseCase().map{
            it.shop.toLocalDomainShop()
        }


//        shopList.value = getShopWithAllLabelList().map {
//            it.shop.toLocalDomainShop()
//        }

//        shopList.value?.map{
//            getLabelsByShopIdUseCase(it.id).let{
//                if( it is Result.Success) labelList.value?.add(it.data)
//            }
//        }

//      getShopWithAllLabelList().map{
//          labelList.value = it.shopLabelWithLabelList.map{
//                it.labels.toLocalDomainLabelList()
//            }
   //     }
    }
}
