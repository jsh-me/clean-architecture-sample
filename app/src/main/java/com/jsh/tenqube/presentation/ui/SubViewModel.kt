package com.jsh.tenqube.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jsh.tenqube.presentation.SingleLiveEvent

class SubViewModel: ViewModel(){
    var shopName = MutableLiveData<String>()
    var shopUrl = MutableLiveData<String>()
    var buttonClicked = SingleLiveEvent<Void>()

    fun deleteButtonClicked(){

    }

    fun editButtonClicked(){

    }

}