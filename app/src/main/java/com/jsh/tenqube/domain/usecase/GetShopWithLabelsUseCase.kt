package com.jsh.tenqube.domain.usecase

import com.jsh.tenqube.data.db.LocalShopAndLabels
import com.jsh.tenqube.domain.Result
import com.jsh.tenqube.domain.entity.Shop
import com.jsh.tenqube.domain.repository.ShopRepository

class GetShopWithLabelsUseCase (
    private val defaultRepository: ShopRepository
) {
    suspend operator fun invoke(): List<LocalShopAndLabels> {
        return defaultRepository.getShop()
    }

}