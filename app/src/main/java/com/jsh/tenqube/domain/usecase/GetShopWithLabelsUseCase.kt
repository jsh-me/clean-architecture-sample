package com.jsh.tenqube.domain.usecase

import com.jsh.tenqube.data.db.ShopAndAllLabels
import com.jsh.tenqube.domain.repository.ShopRepository

class GetShopWithLabelsUseCase (
    private val defaultRepository: ShopRepository
) {
    suspend operator fun invoke(): List<ShopAndAllLabels> {
        return defaultRepository.getShopAndAllLabels()
    }
}