package com.jsh.tenqube.presentation.mapper

import com.jsh.tenqube.domain.entity.DomainLabel.*
import com.jsh.tenqube.domain.entity.DomainShop
import com.jsh.tenqube.domain.entity.DomainShop.*
import com.jsh.tenqube.domain.entity.DomainShopLabel
import com.jsh.tenqube.domain.entity.DomainShopLabel.*
import com.jsh.tenqube.presentation.entity.PresenterLabelEntity.*
import com.jsh.tenqube.presentation.entity.PresenterShopEntity.*
import com.jsh.tenqube.presentation.entity.PresenterShopLabel.*
import com.jsh.tenqube.presentation.entity.PresenterShopLabelModel
import java.util.*

fun List<ShopLabel>.toPresenterShopLabelList(): List<PresenterShopLabelList>{
    return this.map{
        PresenterShopLabelList(it.shop.toPresenterShop(), it.labels.toPresenterLabelList())
    }
}

fun List<Shop>.toPresenterShopList(): List<PresenterShop>{
    return this.map{
        PresenterShop(it.id, it.name, it.imgUrl, it.labelIds)
    }
}

fun List<Label>.toPresenterLabelList(): List<PresenterLabel>{
    return this.map{
        PresenterLabel(it.id, it.name)
    }
}

fun List<List<Label>>.toPresenterLabelListList(): List<List<PresenterLabel>>{
    return this.map{ List ->
        List.map{
            PresenterLabel(it.id, it.name)
        }
    }
}

fun PresenterShop.toDomainShop(): Shop {
    return Shop(this.shopId, this.shopName, this.shopUrl, this.shopLabel)
}

fun Shop.toPresenterShop(): PresenterShop {
    return PresenterShop(shopId = this.id, shopName = this.name, shopUrl = this.imgUrl, shopLabel = this.labelIds)
}

fun PresenterLabel.toDomainLabel(): Label{
    return Label(this.id, this.name)
}

fun PresenterShopLabelModel.PresenterShopLabel.toDomainShopLabel(): SingleShopLabel {
    return SingleShopLabel(this.shop, this.label)
}