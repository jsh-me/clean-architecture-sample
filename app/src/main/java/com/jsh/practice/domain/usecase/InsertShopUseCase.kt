package com.jsh.practice.domain.usecase

import com.jsh.practice.domain.entity.Label
import com.jsh.practice.domain.entity.Shop
import com.jsh.practice.domain.repository.LabelRepository
import com.jsh.practice.domain.repository.ShopRepository
import com.jsh.practice.domain.util.Result

class InsertShopUseCase (
    private val shopRepository: ShopRepository,
    private val labelRepository: LabelRepository
) {
    suspend operator fun invoke(shop: Shop): Result<Boolean> {

        return if (!(shop.id.isNullOrEmpty() || shop.imgUrl.isNullOrEmpty() || shop.name.isNullOrEmpty() || shop.labels.isNullOrEmpty())) {
            shop.labels.map {
                labelRepository.insertLabel(Label(it.id, it.name))
            }
            shopRepository.insertShop(shop)
            Result.Success(true)
        } else
            Result.Success(false)
    }
}