package com.jsh.tenqube.domain.usecase

import com.jsh.tenqube.domain.entity.DomainShopLabel.*
import com.jsh.tenqube.domain.repository.ShopLabelRepository
import com.jsh.tenqube.domain.repository.ShopRepository

class GetShopWithLabelsUseCase (
    private val defaultRepository: ShopLabelRepository
) {
    suspend operator fun invoke(): List<ShopLabel> {
        return defaultRepository.getShopDetails()
    }

}