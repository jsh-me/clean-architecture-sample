package com.jsh.tenqube.domain.usecase

import com.jsh.tenqube.data.shopAndLabel.ShopWithAllLabel
import com.jsh.tenqube.domain.entity.DomainShopLabel
import com.jsh.tenqube.domain.entity.DomainShopLabel.*
import com.jsh.tenqube.domain.repository.ShopRepository

//class GetShopWithLabelsUseCase (
//    private val defaultRepository: ShopRepository
//) {
//    suspend operator fun invoke(): List<ShopWithAllLabel> {
//        return defaultRepository.getShopDetails()
//    }
//
//}

class GetShopWithLabelsUseCase (
    private val defaultRepository: ShopRepository
) {
    suspend operator fun invoke(): List<ShopLabel> {
        return defaultRepository.getShopDetails()
    }

}