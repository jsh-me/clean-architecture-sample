package com.jsh.tenqube.presentation.entity

import com.jsh.tenqube.domain.entity.DomainLabel.*
import com.jsh.tenqube.domain.entity.DomainShop.*

sealed class PresenterShopLabel {
    data class PresenterShopLabelList(
        val shop: Shop,
        val shopLabels: List<Label>
    ) : PresenterShopLabel()
}