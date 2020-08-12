package com.jsh.tenqube.domain.usecase

import com.jsh.tenqube.domain.entity.DomainShop
import com.jsh.tenqube.domain.entity.DomainShopLabel
import com.jsh.tenqube.domain.repository.ShopLabelRepository
import com.jsh.tenqube.domain.repository.ShopRepository
import timber.log.Timber

class InsertShopLabelUseCase (
    private val defaultRepository: ShopLabelRepository
){
    suspend operator fun invoke(shopLabel: DomainShopLabel.SingleShopLabel) {
        Timber.e("insert success")
        defaultRepository.updateShopLabels(shopLabel)
    }
}