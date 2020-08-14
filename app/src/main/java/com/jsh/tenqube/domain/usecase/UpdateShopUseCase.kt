package com.jsh.tenqube.domain.usecase

import com.jsh.tenqube.domain.entity.DomainShop.Shop
import com.jsh.tenqube.domain.repository.ShopRepository
import timber.log.Timber
import com.jsh.tenqube.domain.util.Result

class UpdateShopUseCase (
    private val defaultRepository: ShopRepository
) {
    suspend operator fun invoke(shop: Shop?): Result<Unit> {
        return defaultRepository.updateShop(shop!!).let{
            if(it is Result.Success){
                Timber.e("UpdateSuccess")
                Result.Success(Unit)
            } else {
                Result.Error(Exception("UpdateError"))
            }
        }
    }
}