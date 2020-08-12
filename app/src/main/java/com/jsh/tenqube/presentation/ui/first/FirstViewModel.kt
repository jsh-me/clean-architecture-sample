package com.jsh.tenqube.presentation.ui.first

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsh.tenqube.domain.usecase.*
import com.jsh.tenqube.presentation.SingleLiveEvent
import com.jsh.tenqube.presentation.entity.PresenterShopLabel.PresenterShopLabelList
import com.jsh.tenqube.presentation.mapper.toPresenterShopLabelList
import kotlinx.coroutines.*

class FirstViewModel  @ViewModelInject constructor(
    private val getShopsUseCase: GetShopsUseCase,
    private val getLabelsUseCase: GetLabelsUseCase,
    private val deleteAllShopUseCase: DeleteAllShopUseCase,
    private val deleteAllLabelUseCase: DeleteAllLabelUseCase,
    private val getShopWithLabelsUseCase: GetShopWithLabelsUseCase
): ViewModel() {

    val shopAndLabelList = MutableLiveData<List<PresenterShopLabelList>>().apply{ value = emptyList()}
    val addButtonClicked: SingleLiveEvent<Void> = SingleLiveEvent()
    val openShopListClicked: SingleLiveEvent<ArrayList<String>> = SingleLiveEvent()


    init {
        //deleteAll()
        initData()
    }

    private fun deleteAll() = viewModelScope.launch {
        deleteAllShopUseCase()
        deleteAllLabelUseCase()
    }

    private fun initData() = viewModelScope.launch {
        val deferred = async {
            getLabelsUseCase()
        }
        deferred.await()
        getShopsUseCase()

        viewModelScope.launch {
            shopAndLabelList.value = getShopWithLabelsUseCase().toPresenterShopLabelList()
        }
    }

    fun allLoad(){
        initData()
    }

    fun allDelete() = viewModelScope.launch {
        shopAndLabelList.value = emptyList()
        deleteAllShopUseCase()
        deleteAllLabelUseCase()
    }

    fun openShopDetails(id: String, url: String, name: String, label: String){
        openShopListClicked.value = arrayListOf(id, url, name, label)
    }

    fun addButtonClicked(){
        addButtonClicked.call()
    }
}
