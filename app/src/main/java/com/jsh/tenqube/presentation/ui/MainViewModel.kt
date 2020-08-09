package com.jsh.tenqube.presentation.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsh.tenqube.data.mapper.toLocalDomainLabelList
import com.jsh.tenqube.data.mapper.toLocalDomainShop
import com.jsh.tenqube.domain.entity.Shop
import com.jsh.tenqube.domain.usecase.GetLabelsUseCase
import com.jsh.tenqube.domain.usecase.GetShopWithLabelsUseCase
import com.jsh.tenqube.domain.usecase.GetShopsUseCase
import com.jsh.tenqube.domain.Result
import kotlinx.coroutines.*
import timber.log.Timber

class MainViewModel  @ViewModelInject constructor(
    private val getShopsUseCase: GetShopsUseCase,
    private val getLabelsUseCase: GetLabelsUseCase,
    private val getShopWithLabelsUseCase: GetShopWithLabelsUseCase
): ViewModel() {

    var shopList = MutableLiveData<List<Shop>>()
    var labelList = MutableLiveData<Map<String, String>>() // label id, label name

    init {
//        loadAllLabels()
//        loadAllShops()
        loadData()
    }

    private fun loadData() = viewModelScope.launch{
        val deferred = async {
            getLabelsUseCase().let{
                if(it is Result.Success) labelList.value = it.data
            }
        }
        deferred.await()

        getShopsUseCase().let{ result ->
            if(result is Result.Success) shopList.value = result.data
        }
        Timber.e("first viewmodel end!")
        }

    }

//    private fun loadAllShops() = viewModelScope.launch{ getShopsUseCase() }
//
//    private fun loadAllLabels() = viewModelScope.launch { getLabelsUseCase() }
//
//    private fun loadData()  = viewModelScope.launch{

//        shopList.value = getShopWithLabelsUseCase().map{
//            it.shop.toLocalDomainShop()
//        }

       // Timber.e("Initial Label List: ${getShopWithLabelsUseCase()[0].shop.shopName}")

        //labelList.value = getShopWithLabelsUseCase()[0].labels.toLocalDomainLabelList().toLabelMap()
//    }

//    fun getLabelList(): Map<String, String>? {
//        return labelList.value
//    }
//}
