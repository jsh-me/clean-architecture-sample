package com.jsh.tenqube.domain.usecase

import com.jsh.tenqube.domain.repository.ShopRepository
import com.jsh.tenqube.domain.util.Result

class DeleteShopUseCase(
    private val defaultRepository: ShopRepository
) {
    suspend operator fun invoke(id: String): Result<Unit> {
       return defaultRepository.deleteShop(id)
    }
}