package com.jsh.tenqube.presentation.ui.second

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsh.tenqube.domain.usecase.*
import com.jsh.tenqube.presentation.SingleLiveEvent
import com.jsh.tenqube.presentation.entity.PresenterLabelEntity.*
import com.jsh.tenqube.presentation.entity.PresenterShopEntity
import com.jsh.tenqube.presentation.mapper.toDomainShop
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class SecondViewModel @ViewModelInject constructor(
    private val updateShopUseCase: UpdateShopUseCase,
    private val deleteShopUseCase: DeleteShopUseCase,
    private val insertShopUseCase: InsertShopUseCase
): ViewModel(){
    private val _shopName: MutableLiveData<String> = MutableLiveData()
    val shopName: LiveData<String> = _shopName

    private val _shopId: MutableLiveData<String> = MutableLiveData()
    val shopId: LiveData<String> = _shopId

    private val _shopUrl: MutableLiveData<String> = MutableLiveData()
    val shopUrl: LiveData<String> = _shopUrl

    private val _shopLabels: MutableLiveData<String> = MutableLiveData()
    val shopLabels: LiveData<String> = _shopLabels

    var editButtonClicked: SingleLiveEvent<Void> = SingleLiveEvent()
    var deleteButtonClicked: SingleLiveEvent<Void> = SingleLiveEvent()
    var addImageButtonClicked: SingleLiveEvent<Void> = SingleLiveEvent()
    var addButtonClicked: SingleLiveEvent<Void> = SingleLiveEvent()


    fun deleteButtonClicked(){
        deleteButtonClicked.call()

        viewModelScope.launch {
            deleteShopUseCase(_shopId.value!!)
        }
    }

    fun editButtonClicked(){
        editButtonClicked.call()

        viewModelScope.launch {
            updateShopUseCase(
                PresenterShopEntity.PresenterShop(
                    shopId = _shopId.value!!,
                    shopName = _shopName.value!!,
                    shopUrl = _shopUrl.value!!
                ).toDomainShop()
            )
        }
    }

    fun addButtonClicked(){
        val shopUUID = UUID.randomUUID().toString()
        val labelUUID = UUID.randomUUID().toString()

        if(shopName.value.isNullOrBlank())

        addButtonClicked.call()

        viewModelScope.launch {
            insertShopUseCase(
                PresenterShopEntity.PresenterShop(
                    shopName = _shopName.value!!,
                    shopLabel = listOf(PresenterLabel(labelUUID, shopLabels.value!!)),
                    shopUrl = _shopUrl.value!!,
                    shopId = shopUUID
                ).toDomainShop()
            )
        }
    }

    fun addImageButtonClicked(){
        addImageButtonClicked.call()
    }

    fun buttonVisibility(): Boolean{
        return !_shopName.value.isNullOrEmpty()
    }

    fun setImageUrl(url: String){
        _shopUrl.value = url
    }

    fun setShopInfo(info: ArrayList<String>){
        _shopId.value = info[0]
        _shopName.value = info[2]
        _shopUrl.value = info[1]
        _shopLabels.value = info[3]
    }
}