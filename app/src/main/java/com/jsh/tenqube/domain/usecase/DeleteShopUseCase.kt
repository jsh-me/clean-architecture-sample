package com.jsh.tenqube.domain.usecase

import com.jsh.tenqube.domain.repository.ShopRepository
import timber.log.Timber

class DeleteShopUseCase(
    private val defaultRepository: ShopRepository
) {
    suspend operator fun invoke(id: String){
        Timber.e("delete success")
        defaultRepository.deleteShop(id)
    }
}