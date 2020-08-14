package com.jsh.tenqube.presentation.ui.first

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsh.tenqube.domain.usecase.*
import com.jsh.tenqube.domain.util.Result
import com.jsh.tenqube.presentation.SingleLiveEvent
import com.jsh.tenqube.presentation.entity.PresenterLabelEntity.*
import com.jsh.tenqube.presentation.entity.PresenterShopEntity.*
import com.jsh.tenqube.presentation.mapper.toPresenterShopList
import kotlinx.coroutines.*

class FirstViewModel  @ViewModelInject constructor(
    private val getShopsUseCase: GetShopsUseCase,
    private val deleteAllShopUseCase: DeleteAllShopUseCase
): ViewModel() {

    private val _shopAndLabelList = MutableLiveData<List<PresenterShop>>()
    val shopAndLabelList: LiveData<List<PresenterShop>> = _shopAndLabelList

    val addButtonClicked: SingleLiveEvent<Void> = SingleLiveEvent()
    val openShopListClicked: SingleLiveEvent<ArrayList<String>> = SingleLiveEvent()

    init {
        initData()
    }

    private fun initData() = viewModelScope.launch {
        getShopsUseCase().let{
            if( it is Result.Success){
                _shopAndLabelList.value = it.data.toPresenterShopList()
            }
        }
    }

    fun allLoadButtonClick(){
        initData()
    }

    fun allDeleteButtonClick() = viewModelScope.launch {
        _shopAndLabelList.value = emptyList()
        deleteAllShopUseCase()
    }

    fun openShopDetails(id: String, url: String, name: String, label: String){
        openShopListClicked.value = arrayListOf(id, url, name, label)
    }

    fun addButtonClicked(){
        addButtonClicked.call()
    }
}
