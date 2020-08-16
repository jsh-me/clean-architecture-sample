package com.jsh.tenqube.presentation.ui.second

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsh.tenqube.domain.usecase.*
import com.jsh.tenqube.presentation.SingleLiveEvent
import kotlinx.coroutines.launch
import java.util.*
import com.jsh.tenqube.domain.util.Result
import com.jsh.tenqube.presentation.entity.PresenterLabel
import com.jsh.tenqube.presentation.entity.PresenterShop
import com.jsh.tenqube.presentation.mapper.toDomainShop
import com.jsh.tenqube.presentation.util.toListLabel
import com.jsh.tenqube.presentation.util.toStringLabel

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

    fun editButtonClicked() {
        viewModelScope.launch {
            updateShopUseCase(
                PresenterShop(
                    shopId = _shopId.value!!,
                    shopName = shopName.value!!,
                    shopUrl = shopUrl.value!!,
                    shopLabel = listOf(PresenterLabel("", shopLabels.value!!))
                ).toDomainShop()
            ).let {
                if (it is Result.Success) {
                    editButtonClicked.value = it.data
                }
            }
        }
    }

    fun addButtonClicked(){
        val shopUUID = UUID.randomUUID().toString()

        viewModelScope.launch {
                insertShopUseCase(
                    PresenterShop(
                        shopName = shopName.value!!,
                        shopLabel = shopLabels.value?.toListLabel()!!,
                        shopUrl = shopUrl.value!!,
                        shopId = shopUUID
                    ).toDomainShop()
                ).let {
                    if (it is Result.Success) {
                        addButtonClicked.value = it.data
                    }
                }
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

    fun setShopInfo(shop: PresenterShop){
        _shopId.value = shop.shopId
        shopUrl.value = shop.shopUrl
        shopName.value = shop.shopName
        shopLabels.value = shop.shopLabel.toStringLabel()
    }
}