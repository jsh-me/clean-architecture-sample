package com.jsh.practice.presentation.ui.first

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsh.practice.domain.usecase.*
import com.jsh.practice.domain.util.Result
import com.jsh.practice.presentation.SingleLiveEvent
import com.jsh.practice.presentation.entity.PresenterShop
import com.jsh.practice.presentation.mapper.toPresenterShopList
import kotlinx.coroutines.*

class FirstViewModel  @ViewModelInject constructor (
    private val getShopsUseCase: GetShopsUseCase,
    private val deleteAllShopUseCase: DeleteAllShopUseCase
): ViewModel() {

    private val _shopAndLabelList = MutableLiveData<List<PresenterShop>>()
    val shopAndLabelList: LiveData<List<PresenterShop>> = _shopAndLabelList

    private val _addShop = SingleLiveEvent<Void>()
    val addShop: SingleLiveEvent<Void> = _addShop

    private val _openShopDetail = SingleLiveEvent<PresenterShop>()
    var openShopDetail: SingleLiveEvent<PresenterShop> = _openShopDetail

    private var updated: Boolean = true

    init {
        initData(updated)
    }

    private fun initData(isUpdated: Boolean) = viewModelScope.launch {
        getShopsUseCase(isUpdated).let{
            if( it is Result.Success){
                _shopAndLabelList.value = it.data.toPresenterShopList()
            }
        }
        updated = false
    }

    fun reLoadButtonClick() {
        updated = true
        initData(updated)
    }

    fun allDeleteButtonClick() = viewModelScope.launch {
        deleteAllShopUseCase().let {
            if(it is Result.Success) _shopAndLabelList.value = emptyList()
        }
    }

    fun openShopDetails(shop: PresenterShop) {
        _openShopDetail.value = shop
    }

    fun addButtonClicked() {
        _addShop.call()
    }
}
