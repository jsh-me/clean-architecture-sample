package com.jsh.tenqube.presentation.ui.second

import android.widget.Toast
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
import kotlin.coroutines.coroutineContext

class SecondViewModel @ViewModelInject constructor(
    private val updateShopUseCase: UpdateShopUseCase,
    private val deleteShopUseCase: DeleteShopUseCase,
    private val insertShopUseCase: InsertShopUseCase
): ViewModel(){
    //2-way data binding
    var shopName: MutableLiveData<String?> = MutableLiveData()
    var shopUrl: MutableLiveData<String?> = MutableLiveData()
    var shopLabels: MutableLiveData<String?> = MutableLiveData()

    private val _shopId: MutableLiveData<String?> = MutableLiveData()
    val shopId: LiveData<String?> = _shopId

    var editButtonClicked: SingleLiveEvent<Boolean> = SingleLiveEvent()
    var deleteButtonClicked: SingleLiveEvent<Boolean> = SingleLiveEvent()
    var addImageButtonClicked: SingleLiveEvent<Void> = SingleLiveEvent()
    var addButtonClicked: SingleLiveEvent<Boolean> = SingleLiveEvent()


    fun deleteButtonClicked(){
        _shopId.value?.let {
            viewModelScope.launch {
                deleteShopUseCase(it)
            }
            deleteButtonClicked.value = true
        }?: run{
            deleteButtonClicked.value = false
        }
    }

    fun editButtonClicked(){
        if(!(_shopId.value.isNullOrEmpty() || shopName.value.isNullOrEmpty() || shopUrl.value.isNullOrEmpty() || shopLabels.value.isNullOrEmpty())) {
            viewModelScope.launch {
                updateShopUseCase(
                    PresenterShopEntity.PresenterShop(
                        shopId = _shopId.value!!,
                        shopName = shopName.value!!,
                        shopUrl = shopUrl.value!!
                    ).toDomainShop()
                )
            }
            editButtonClicked.value = true
        }
        else {
            editButtonClicked.value = false
        }
    }

    fun addButtonClicked(){
        val shopUUID = UUID.randomUUID().toString()
        val labelUUID = UUID.randomUUID().toString()

        if(!(_shopId.value.isNullOrEmpty() || shopName.value.isNullOrEmpty() || shopUrl.value.isNullOrEmpty() || shopLabels.value.isNullOrEmpty())) {
            viewModelScope.launch {
                insertShopUseCase(
                    PresenterShopEntity.PresenterShop(
                        shopName = shopName.value!!,
                        shopLabel = listOf(PresenterLabel(labelUUID, shopLabels.value!!)),
                        shopUrl = shopUrl.value!!,
                        shopId = shopUUID
                    ).toDomainShop()
                )
            }
            addButtonClicked.value = true
        }
        else {
            addButtonClicked.value = false
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
        _shopId.value = info[0]
        shopUrl.value = info[1]
        shopName.value = info[2]
        shopLabels.value = info[3]
    }
}