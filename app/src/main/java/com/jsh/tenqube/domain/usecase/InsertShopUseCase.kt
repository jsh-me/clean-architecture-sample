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
    suspend operator fun invoke(shop: Shop): Result<Boolean> {

        return if(!(shop.id.isNullOrEmpty() || shop.imgUrl.isNullOrEmpty() || shop.name.isNullOrEmpty() || shop.labels.isNullOrEmpty())){
            shop.labels.map{
                labelRepository.insertLabel(Label(it.id, it.name))
            }
            shopRepository.insertShop(shop)
            Result.Success(true)
        } else
            Result.Success(false)
    }
}