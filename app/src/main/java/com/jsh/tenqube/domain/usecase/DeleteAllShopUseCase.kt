package com.jsh.tenqube.domain.usecase

import com.jsh.tenqube.domain.repository.ShopRepository

class DeleteAllShopUseCase (
    private val defaultRepository: ShopRepository
){
    suspend operator fun invoke(){
        defaultRepository.deleteAllShop()
    }
}