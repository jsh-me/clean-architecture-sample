package com.jsh.tenqube.domain.usecase

import com.jsh.tenqube.domain.entity.DomainShop.Shop
import com.jsh.tenqube.domain.repository.ShopRepository
import com.jsh.tenqube.domain.util.Result

class UpdateShopUseCase (
    private val defaultRepository: ShopRepository
) {
    suspend operator fun invoke(shop: Shop): Result<Boolean> {

        return if(!(shop.id.isNullOrEmpty() || shop.name.isNullOrEmpty() || shop.imgUrl.isNullOrEmpty() || shop.labels.isNullOrEmpty())) {
            defaultRepository.updateShop(shop)
             Result.Success(true)
        } else Result.Success(false)
    }
}