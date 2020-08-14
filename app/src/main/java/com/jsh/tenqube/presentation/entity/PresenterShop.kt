package com.jsh.tenqube.presentation.entity

import com.jsh.tenqube.presentation.entity.PresenterLabelEntity.PresenterLabel

sealed class PresenterShopEntity {
    data class PresenterShop(
        val shopId: String,
        val shopName: String,
        val shopUrl: String,
        val shopLabel: List<PresenterLabel>
    ): PresenterShopEntity()
}