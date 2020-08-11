package com.jsh.tenqube.domain.entity

import com.jsh.tenqube.domain.entity.DomainLabel.Label
import com.jsh.tenqube.domain.entity.DomainShop.Shop

sealed class DomainShopLabel {
    data class ShopLabel(
        var shop: Shop,
        var labels: List<Label>
    ) : DomainShopLabel()
}