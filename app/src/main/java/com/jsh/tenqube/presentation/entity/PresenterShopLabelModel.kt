package com.jsh.tenqube.presentation.entity

sealed class PresenterShopLabelModel {
    data class PresenterShopLabel(
        val shop: String,
        val label: String
    ) : PresenterShopLabelModel()
}