package com.jsh.tenqube.presentation.ui.second

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsh.tenqube.domain.usecase.*
import com.jsh.tenqube.presentation.SingleLiveEvent
import com.jsh.tenqube.presentation.entity.PresenterLabelEntity
import com.jsh.tenqube.presentation.entity.PresenterShopEntity
import com.jsh.tenqube.presentation.entity.PresenterShopLabel
import com.jsh.tenqube.presentation.entity.PresenterShopLabelModel
import com.jsh.tenqube.presentation.mapper.toDomainLabel
import com.jsh.tenqube.presentation.mapper.toDomainShop
import com.jsh.tenqube.presentation.mapper.toDomainShopLabel
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class SecondViewModel @ViewModelInject constructor(
    private val updateShopInfoUseCase: UpdateShopInfoUseCase,
    private val deleteShopInfoUseCase: DeleteShopInfoUseCase,
    private val insertShopInfoUseCase: InsertShopInfoUseCase,
    private val insertLabelInfoUseCase: InsertLabelInfoUseCase,
    private val insertShopLabelUseCase: InsertShopLabelUseCase
): ViewModel(){
    var shopName: MutableLiveData<String> = MutableLiveData()
    var shopId: MutableLiveData<String> = MutableLiveData()
    var shopUrl: MutableLiveData<String> = MutableLiveData()
    var shopLabels: MutableLiveData<String> = MutableLiveData()
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
                PresenterShopEntity.PresenterShop(shopId = shopId.value!!, shopName = shopName.value!!, shopUrl = shopUrl.value!!, shopLabel = emptyList() ).toDomainShop()
            )
        }
    }

    fun addButtonClicked(){
        val shopUUID = UUID.randomUUID().toString()
        val labelUUID = UUID.randomUUID().toString()

        if(shopName.value.isNullOrBlank())

        addButtonClicked.call()

        viewModelScope.launch {
            insertShopInfoUseCase(
                PresenterShopEntity.PresenterShop(shopName = shopName.value!!, shopLabel = emptyList(), shopUrl = shopUrl.value!!, shopId = shopUUID).toDomainShop()
            )
            insertLabelInfoUseCase(
                PresenterLabelEntity.PresenterLabel(id = labelUUID, name = shopLabels.value!!).toDomainLabel()
            )
            insertShopLabelUseCase(
                PresenterShopLabelModel.PresenterShopLabel(shop = shopUUID, label = labelUUID).toDomainShopLabel()
            )
        }
    }

    fun addImageButtonClicked(){
        addImageButtonClicked.call()
    }

    fun buttonVisibility(): Boolean{
        return !shopName.value.isNullOrEmpty()
    }

    fun setImageUrl(url: String){
        shopUrl.value = url
    }

    fun setShopInfo(info: ArrayList<String>){
        shopId.value = info[0]
        shopName.value = info[2]
        shopUrl.value = info[1]
        shopLabels.value = info[3]
    }
}