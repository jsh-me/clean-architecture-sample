package com.jsh.tenqube.domain.usecase

import com.jsh.tenqube.domain.entity.Shop
import com.jsh.tenqube.domain.Result
import com.jsh.tenqube.domain.repository.LabelRepository
import com.jsh.tenqube.domain.repository.ShopRepository

class GetShopsUseCase (
    private val defaultRepository: ShopRepository
) {
    suspend operator fun invoke(isUpdated: Boolean = false): Result<List<Shop>> {
        return defaultRepository.getShops(isUpdated)
    }

}