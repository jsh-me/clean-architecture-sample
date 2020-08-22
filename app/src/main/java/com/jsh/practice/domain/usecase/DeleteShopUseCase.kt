package com.jsh.practice.domain.usecase

import com.jsh.practice.domain.repository.ShopRepository
import com.jsh.practice.domain.util.Result

class DeleteShopUseCase(
    private val defaultRepository: ShopRepository
) {
    suspend operator fun invoke(id: String): Result<Unit> {
        return defaultRepository.deleteShop(id)
    }
}