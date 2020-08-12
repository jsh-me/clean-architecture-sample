package com.jsh.tenqube.domain.usecase

import com.jsh.tenqube.domain.repository.ShopLabelRepository
import com.jsh.tenqube.domain.repository.ShopRepository

class DeleteAllShopUseCase (
    private val defaultRepository: ShopRepository,
    private val shopLabelRepository: ShopLabelRepository
) {
    suspend operator fun invoke(){
        defaultRepository.deleteAllShop()
        shopLabelRepository.deleteAllShopLabels()
    }
}