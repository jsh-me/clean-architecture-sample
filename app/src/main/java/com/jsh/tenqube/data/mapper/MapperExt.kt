package com.jsh.tenqube.data.mapper

import com.jsh.tenqube.data.dto.LabelModel
import com.jsh.tenqube.data.dto.ShopModel
import com.jsh.tenqube.data.label.local.LocalLabelModel
import com.jsh.tenqube.data.shop.local.LocalShopModel
import com.jsh.tenqube.domain.entity.Label
import com.jsh.tenqube.domain.entity.Shop

fun List<LabelModel>.toDomainLabelList(): List<Label>{
    return this.map{
        Label(it.id, it.name)
    }
}

fun List<ShopModel>.toDomainShopList(): List<Shop>{
    return this.map{
        Shop(it.id, it.name, it.imgUrl, it.labelIds)
    }
}

fun List<LocalLabelModel>.toLocalDomainLabelList(): List<Label>{
    return this.map{
        Label(it.id, it.name)
    }
}

fun List<LocalShopModel>.toLocalDomainShopList(): List<Shop>{
    val tempList = listOf<String>("#cute", "#sexy")
    return this.map{
        Shop(it.id, it.shopName, it.shopUrl, tempList)
    }
}

fun Shop.toDataShopModel(): ShopModel{
    return ShopModel(
        id = this.id,
        name = this.name,
        imgUrl = this.imgUrl,
        labelIds = this.labelIds,
        createAt = "",
        deleteAt = "",
        isDeleted = 0,
        linkUrl = this.imgUrl,
        quality = 0
    )
}

fun LabelModel.toDomainLabel(): Label{
    return Label(this.id, this.name)
}

fun ShopModel.toDomainShop(): Shop{
    return Shop(this.id, this.name, this.imgUrl, this.labelIds)
}

fun LocalLabelModel.toLocalDomainLabel(): Label {
    return Label(this.id, this.name)
}


fun Label.toDataLocalLabelModel(): LocalLabelModel{
    return LocalLabelModel(this.id, this.name)
}

fun LocalShopModel.toLocalDomainShop(): Shop{
    val tempList = listOf<String>("#cute", "#sexy")
    return Shop(this.id, this.shopName, this.shopUrl, tempList)
}

fun Shop.toLocalDataShopModel(): LocalShopModel {
    return LocalShopModel(
        this.id,
        this.name,
        this.imgUrl,
        "tempList"
    )
}