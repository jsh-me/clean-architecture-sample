package com.jsh.tenqube.domain.usecase

import com.jsh.tenqube.domain.entity.DomainLabel.*
import com.jsh.tenqube.domain.entity.DomainShop.*
import com.jsh.tenqube.domain.repository.LabelRepository
import com.jsh.tenqube.domain.repository.ShopRepository
import timber.log.Timber

class InsertShopUseCase (
    private val shopRepository: ShopRepository,
    private val labelRepository: LabelRepository
){
    suspend operator fun invoke(shop: Shop) {
        Timber.e("insert success")
        shopRepository.insertShop(shop)
        shop.labels?.map{
            labelRepository.insertLabel(Label(it.id, it.name))
        }
    }
}