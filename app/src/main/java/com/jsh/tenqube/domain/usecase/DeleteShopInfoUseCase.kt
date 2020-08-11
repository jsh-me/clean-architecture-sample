package com.jsh.tenqube.domain.usecase

import com.jsh.tenqube.domain.repository.ShopRepository

class DeleteShopInfoUseCase(
    private val defaultRepository: ShopRepository
){
    suspend operator fun invoke(id: String){
        defaultRepository.deleteShop(id)
    }
}