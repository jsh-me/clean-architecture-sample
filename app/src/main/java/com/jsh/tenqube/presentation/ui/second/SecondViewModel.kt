package com.jsh.tenqube.presentation.ui.second

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsh.tenqube.domain.entity.DomainShop.Shop
import com.jsh.tenqube.domain.usecase.DeleteShopInfoUseCase
import com.jsh.tenqube.domain.usecase.UpdateShopInfoUseCase
import com.jsh.tenqube.presentation.SingleLiveEvent
import kotlinx.coroutines.launch

class SecondViewModel @ViewModelInject constructor(
    private val updateShopInfoUseCase: UpdateShopInfoUseCase,
    private val deleteShopInfoUseCase: DeleteShopInfoUseCase
): ViewModel(){
    var shopName: MutableLiveData<String> = MutableLiveData()
    var shopId: MutableLiveData<String> = MutableLiveData()
    var shopUrl: MutableLiveData<String> = MutableLiveData()
    var shopLabels: MutableLiveData<ArrayList<String>> = MutableLiveData()
    var editButtonClicked: SingleLiveEvent<Void> = SingleLiveEvent()
    var deleteButtonClicked: SingleLiveEvent<Void> = SingleLiveEvent()
    var addImageButtonClicked: SingleLiveEvent<Void> = SingleLiveEvent()
    var addButtonClicked: SingleLiveEvent<Void> = SingleLiveEvent()


    fun deleteButtonClicked(){
        deleteButtonClicked.call()

        viewModelScope.launch {
            deleteShopInfoUseCase(shopId.value!!)
        }
    }

    fun editButtonClicked(){
        editButtonClicked.call()

        viewModelScope.launch {
            updateShopInfoUseCase(
                Shop(shopId.value!!, shopName.value!!, shopUrl.value!!, shopLabels.value!!)
            )
        }
    }

    fun addButtonClicked(){
        addButtonClicked.call()
    }

    fun addImageButtonClicked(){
        addImageButtonClicked.call()
    }

    fun buttonVisibility(): Boolean{
        return !shopName.value.isNullOrEmpty()
    }

    fun setShopName(name: String){
        shopName.value = name
    }

    fun setShopId(id: String){
        shopId.value =id
    }

    fun setShopLabels(labels: ArrayList<String>){
        shopLabels.value = labels
    }

    fun setShopUrl(url: String){
        shopUrl.value = url
    }

}