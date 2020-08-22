package com.jsh.practice.domain.usecase

import com.jsh.practice.domain.entity.Shop
import com.jsh.practice.domain.repository.LabelRepository
import com.jsh.practice.domain.repository.ShopRepository
import com.jsh.practice.domain.util.Result

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