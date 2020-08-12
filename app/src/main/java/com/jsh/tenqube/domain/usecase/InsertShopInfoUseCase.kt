package com.jsh.tenqube.domain.usecase

import com.jsh.tenqube.domain.entity.DomainShop.*
import com.jsh.tenqube.domain.repository.ShopLabelRepository
import com.jsh.tenqube.domain.repository.ShopRepository
import timber.log.Timber

class InsertShopInfoUseCase (
    private val defaultRepository: ShopRepository,
    private val shopLabelRepository: ShopLabelRepository
){
    suspend operator fun invoke(shop: Shop) {
        Timber.e("insert success")
        defaultRepository.insertShop(shop)
        shopLabelRepository.insertShopLabels(shop)
    }
}