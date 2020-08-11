package com.jsh.tenqube.domain.usecase

import com.jsh.tenqube.domain.entity.DomainShop.Shop
import com.jsh.tenqube.domain.repository.ShopRepository

class UpdateShopInfoUseCase (
    private val defaultRepository: ShopRepository
){
    suspend operator fun invoke(shop: Shop){
        return defaultRepository.updateShop(shop)
    }
}