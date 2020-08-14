package com.jsh.tenqube.domain.usecase

import com.jsh.tenqube.domain.entity.DomainLabel.*
import com.jsh.tenqube.domain.entity.DomainShop.*
import com.jsh.tenqube.domain.repository.LabelRepository
import com.jsh.tenqube.domain.repository.ShopRepository
import com.jsh.tenqube.domain.util.Result

class InsertShopUseCase (
    private val shopRepository: ShopRepository,
    private val labelRepository: LabelRepository
){
    suspend operator fun invoke(shop: Shop): Result<Unit> {
        shop.labels.map{
            labelRepository.insertLabel(Label(it.id, it.name))
        }
        return shopRepository.insertShop(shop)
    }
}