package com.jsh.tenqube.presentation.mapper

import com.jsh.tenqube.domain.entity.DomainShopLabel.ShopLabel
import com.jsh.tenqube.presentation.entity.PresenterShopLabel.*

fun List<ShopLabel>.toShopLabelList(): List<PresenterShopLabelList>{
    return this.map{
        PresenterShopLabelList(it.shop, it.labels)
    }
}