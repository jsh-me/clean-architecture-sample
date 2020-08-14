package com.jsh.tenqube.data.mapper

import com.jsh.tenqube.data.dto.LabelModel
import com.jsh.tenqube.data.dto.ShopModel
import com.jsh.tenqube.data.source.label.local.DataLabel.*
import com.jsh.tenqube.data.source.shop.local.DataShop
import com.jsh.tenqube.data.source.shop.local.DataShopLabel
import com.jsh.tenqube.data.source.shop.local.DataShopLabel.LocalShopLabelModel
import com.jsh.tenqube.data.source.shop.local.ShopWithAllLabel
import com.jsh.tenqube.domain.entity.DomainLabel.*
import com.jsh.tenqube.domain.entity.DomainShop.*

fun List<LabelModel>.toDomainLabelList(): List<Label>{
    return this.map{
        Label(it.id, it.name)
    }
}

//우선 label id 만 넣는당.
fun List<ShopModel>.toDomainShopList(): List<Shop>{
    return this.map{
        Shop(it.id, it.name, it.imgUrl, it.labelIds.map { id -> Label(id, "") })
    }
}

fun List<LocalLabelModel>.toLocalDomainLabelList(): List<Label>{
    return this.map{
        Label(it.id, it.name)
    }
}

fun List<DataShop.LocalShopModel>.toLocalDomainShopList(): List<Shop>{
    return this.map{
        Shop(it.id, it.shopName, it.shopUrl, arrayListOf())
    }
}

fun LocalLabelModel.toDomainLabel(): Label {
    return Label(this.id, this.name)
}


fun Label.toDataLocalLabelModel(): LocalLabelModel {
    return LocalLabelModel(this.id, this.name)
}

fun List<Label>.toDataLocalLabelList(): List<LocalLabelModel>{
    return this.map{
        LocalLabelModel(it.id, it.name)
    }
}

fun DataShop.LocalShopModel.toLocalDomainShop(): Shop {
    return Shop(this.id, this.shopName, this.shopUrl, arrayListOf())
}

fun Shop.toLocalDataShopModel(): DataShop.LocalShopModel {
    return DataShop.LocalShopModel(id = this.id, shopName = this.name, shopUrl = this.imgUrl)
}