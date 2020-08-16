package com.jsh.tenqube.domain.usecase

import com.jsh.tenqube.domain.entity.Shop
import com.jsh.tenqube.domain.repository.LabelRepository
import com.jsh.tenqube.domain.repository.ShopRepository
import com.jsh.tenqube.domain.util.Result

class GetShopsUseCase (
    private val shopRepository: ShopRepository,
    private val labelRepository: LabelRepository
) {
    suspend operator fun invoke(isUpdated: Boolean): Result<List<Shop>> {
        return when (labelRepository.getLabels(isUpdated)) {
            is Result.Success -> shopRepository.getShops(isUpdated)
            else -> Result.Error(Exception("Load Failed"))
        }
    }
}