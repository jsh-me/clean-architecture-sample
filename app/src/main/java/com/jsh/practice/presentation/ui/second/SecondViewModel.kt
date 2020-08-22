package com.jsh.practice.presentation.ui.second

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsh.practice.domain.usecase.*
import com.jsh.practice.presentation.SingleLiveEvent
import kotlinx.coroutines.launch
import java.util.*
import com.jsh.practice.domain.util.Result
import com.jsh.practice.presentation.entity.PresenterLabel
import com.jsh.practice.presentation.entity.PresenterShop
import com.jsh.practice.presentation.mapper.toDomainShop
import com.jsh.practice.presentation.util.toListLabel
import com.jsh.practice.presentation.util.toStringLabel
import timber.log.Timber

class SecondViewModel @ViewModelInject constructor (
    private val updateShopUseCase: UpdateShopUseCase,
    private val deleteShopUseCase: DeleteShopUseCase,
    private val insertShopUseCase: InsertShopUseCase
): ViewModel(){
    //2-way data binding
    var shopName: MutableLiveData<String> = MutableLiveData()
    var shopUrl: MutableLiveData<String> = MutableLiveData()
    var shopLabels: MutableLiveData<String> = MutableLiveData()

    private val _shopId: MutableLiveData<String> = MutableLiveData()
    val shopId: LiveData<String> = _shopId

    private val _editShop: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val editShop: SingleLiveEvent<Boolean> = _editShop

    private val _deleteShop: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val deleteShop: SingleLiveEvent<Boolean> = _deleteShop

    private val _addShopImage: SingleLiveEvent<Void> = SingleLiveEvent()
    val addShopImage: SingleLiveEvent<Void> = _addShopImage

    private val _addShop: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val addShop: SingleLiveEvent<Boolean> = _addShop


    fun deleteButtonClicked() {
        _shopId.value?.let {
            viewModelScope.launch {
                deleteShopUseCase(it)
            }
            _deleteShop.value = true
        }?: run{
            _deleteShop.value = false
        }
    }

    fun editButtonClicked() {
        viewModelScope.launch {
            updateShopUseCase(
                PresenterShop(
                    shopId = _shopId.value!!,
                    shopName = shopName.value!!,
                    shopUrl = shopUrl.value!!,
                    shopLabel = shopLabels.value!!.toListLabel()
                ).toDomainShop()
            ).let {
                if (it is Result.Success) {
                    _editShop.value = it.data
                }
            }
        }
    }

    fun addButtonClicked() {
        val shopUUID = UUID.randomUUID().toString()

        viewModelScope.launch {
            insertShopUseCase(
                PresenterShop(
                    shopName = shopName.value.toString(),
                    shopLabel = shopLabels.value.toListLabel(),
                    shopUrl = shopUrl.value.toString(),
                    shopId = shopUUID
                ).toDomainShop()
            ).let {
                if (it is Result.Success) {
                    _addShop.value = it.data
                }
            }
        }
    }

    fun addImageButtonClicked() {
        _addShopImage.call()
    }

    fun buttonVisibility(): Boolean {
        return !shopName.value.isNullOrEmpty()
    }

    fun setImageUrl(url: String) {
        shopUrl.value = url
    }

    fun setShopInfo(shop: PresenterShop) {
        _shopId.value = shop.shopId
        shopUrl.value = shop.shopUrl
        shopName.value = shop.shopName
        shopLabels.value = shop.shopLabel.toStringLabel()
    }
}