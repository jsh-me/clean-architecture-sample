package com.jsh.tenqube.presentation.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsh.tenqube.domain.Result
import com.jsh.tenqube.domain.entity.Shop
import com.jsh.tenqube.domain.usecase.GetLabelsUseCase
import com.jsh.tenqube.domain.usecase.GetShopsUseCase
import kotlinx.coroutines.*
import timber.log.Timber

class MainViewModel  @ViewModelInject constructor(
    private val getShopsUseCase: GetShopsUseCase,
    private val getLabelsUseCase: GetLabelsUseCase
): ViewModel() {

    var shopList = MutableLiveData<List<Shop>>()
    private var labelList = MutableLiveData<Map<String, String>>() // label id, label name

    init {
        loadData()
    }

    private fun loadData() = viewModelScope.launch{
        val deferred = async {
            getLabelsUseCase(false).let{
                if(it is Result.Success) labelList.value = it.data
            }
        }
        deferred.await()

        getShopsUseCase(false).let{ result ->
            if(result is Result.Success) shopList.value = result.data
        }
        Timber.e("first viewmodel end!")

    }

    fun getLabelList(): Map<String, String>? {
        return labelList.value
    }
}
