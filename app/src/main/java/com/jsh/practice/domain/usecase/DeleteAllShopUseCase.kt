package com.jsh.practice.domain.usecase

import com.jsh.practice.domain.repository.ShopRepository
import com.jsh.practice.domain.util.Result
import java.lang.Exception

class DeleteAllShopUseCase (
    private val shopRepository: ShopRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        shopRepository.deleteAllShop().let {
            return if (it is Result.Success) {
                Result.Success(Unit)
            } else {
                Result.Error(Exception("delete all error"))
            }
        }
    }
}