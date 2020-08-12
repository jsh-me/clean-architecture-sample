package com.jsh.tenqube.presentation.entity

import java.util.*

sealed class PresenterShopEntity {
    data class PresenterShop(
        val shopId: String,
        val shopName: String,
        val shopUrl: String,
        val shopLabel: List<String>
    ): PresenterShopEntity()
}