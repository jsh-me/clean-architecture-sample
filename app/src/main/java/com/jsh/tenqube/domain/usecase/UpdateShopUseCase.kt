package com.jsh.tenqube.domain.usecase

import com.jsh.tenqube.domain.entity.DomainShop.Shop
import com.jsh.tenqube.domain.repository.ShopRepository
import timber.log.Timber

class UpdateShopUseCase (
    private val defaultRepository: ShopRepository
) {
    suspend operator fun invoke(shop: Shop){
        Timber.e("update success")
        return defaultRepository.updateShop(shop)
    }
}