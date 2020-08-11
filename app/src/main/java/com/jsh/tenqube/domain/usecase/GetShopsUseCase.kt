package com.jsh.tenqube.domain.usecase

import com.jsh.tenqube.domain.Result
import com.jsh.tenqube.domain.entity.DomainShop
import com.jsh.tenqube.domain.entity.DomainShop.*
import com.jsh.tenqube.domain.repository.LabelRepository
import com.jsh.tenqube.domain.repository.ShopRepository

class GetShopsUseCase (
    private val defaultRepository: ShopRepository
) {
    suspend operator fun invoke(): Result<List<Shop>> {
        return defaultRepository.getShops()
    }
}