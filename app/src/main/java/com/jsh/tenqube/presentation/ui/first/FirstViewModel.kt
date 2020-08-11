package com.jsh.tenqube.presentation.ui.first

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsh.tenqube.domain.usecase.*
import com.jsh.tenqube.presentation.SingleLiveEvent
import com.jsh.tenqube.presentation.entity.PresenterShopLabel.PresenterShopLabelList
import com.jsh.tenqube.presentation.mapper.toShopLabelList
import kotlinx.coroutines.*

class FirstViewModel  @ViewModelInject constructor(
    private val getShopsUseCase: GetShopsUseCase,
    private val getLabelsUseCase: GetLabelsUseCase,
    private val deleteAllShopUseCase: DeleteAllShopUseCase,
    private val deleteAllLabelUseCase: DeleteAllLabelUseCase,
    private val getShopWithLabelsUseCase: GetShopWithLabelsUseCase
): ViewModel() {

    val shopAndLabelList = MutableLiveData<List<PresenterShopLabelList>>()
    val addButtonClicked: SingleLiveEvent<Void> = SingleLiveEvent()

    init {
       // deleteAll()
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
            shopAndLabelList.value = getShopWithLabelsUseCase().toShopLabelList()
        }
    }


    fun addButtonClicked(){
        addButtonClicked.call()
    }
}
