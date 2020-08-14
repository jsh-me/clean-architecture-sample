package com.jsh.tenqube.domain.usecase

import com.jsh.tenqube.domain.repository.ShopRepository

class DeleteAllShopUseCase (
    private val shopRepository: ShopRepository
) {
    suspend operator fun invoke(){
       shopRepository.deleteAllShop()
    }
}