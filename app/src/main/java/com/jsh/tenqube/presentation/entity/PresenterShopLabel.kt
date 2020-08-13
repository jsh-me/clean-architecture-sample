package com.jsh.tenqube.presentation.entity

import com.jsh.tenqube.presentation.entity.PresenterLabelEntity.PresenterLabel
import com.jsh.tenqube.presentation.entity.PresenterShopEntity.PresenterShop

sealed class PresenterShopLabel {
    data class PresenterShopLabelList(
        val shop: PresenterShop,
        val shopLabels: List<PresenterLabel>
    ) : PresenterShopLabel()
}