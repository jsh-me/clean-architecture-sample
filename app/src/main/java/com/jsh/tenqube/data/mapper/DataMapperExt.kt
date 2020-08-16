package com.jsh.tenqube.data.mapper

import com.jsh.tenqube.data.dto.LabelModel
import com.jsh.tenqube.data.source.label.local.LocalLabelModel
import com.jsh.tenqube.data.source.shop.local.LocalShopModel
import com.jsh.tenqube.domain.entity.Label
import com.jsh.tenqube.domain.entity.Shop

fun List<LabelModel>.toDomainLabelList(): List<Label> {
    return this.map {
        Label(it.id, it.name)
    }
}

fun List<LocalLabelModel>.toLocalDomainLabelList(): List<Label> {
    return this.map {
        Label(it.id, it.name)
    }
}

fun LocalLabelModel.toDomainLabel(): Label {
    return Label(this.id, this.name)
}

fun Label.toDataLocalLabelModel(): LocalLabelModel {
    return LocalLabelModel(this.id, this.name)
}

fun LocalShopModel.toLocalDomainShop(): Shop {
    return Shop(this.id, this.shopName, this.shopUrl, arrayListOf())
}

fun Shop.toLocalDataShopModel(): LocalShopModel {
    return LocalShopModel(id = this.id, shopName = this.name, shopUrl = this.imgUrl)
}