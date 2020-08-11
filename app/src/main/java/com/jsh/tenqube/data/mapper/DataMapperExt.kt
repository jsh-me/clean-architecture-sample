package com.jsh.tenqube.data.mapper

import com.jsh.tenqube.data.dto.LabelModel
import com.jsh.tenqube.data.dto.ShopModel
import com.jsh.tenqube.data.label.local.DataLabel
import com.jsh.tenqube.data.label.local.DataLabel.*
import com.jsh.tenqube.data.shop.local.DataShop
import com.jsh.tenqube.data.shopAndLabel.DataShopLocal
import com.jsh.tenqube.data.shopAndLabel.ShopWithAllLabel
import com.jsh.tenqube.domain.entity.DomainLabel
import com.jsh.tenqube.domain.entity.DomainLabel.*
import com.jsh.tenqube.domain.entity.DomainShop
import com.jsh.tenqube.domain.entity.DomainShop.*
import com.jsh.tenqube.domain.entity.DomainShopLabel
import com.jsh.tenqube.domain.entity.DomainShopLabel.*


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

fun List<DataShop.LocalShopModel>.toLocalDomainShopList(): List<Shop>{
    return this.map{
        Shop(it.id, it.shopName, it.shopUrl, arrayListOf())
    }
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


fun ShopLabel.toDataShopLabel(): ShopWithAllLabel {
    return ShopWithAllLabel(this.shop.toLocalDataShopModel(), this.labels.toDataLocalLabelList())
}

fun ShopWithAllLabel.toDomainShopLabel(): ShopLabel {
    return ShopLabel(this.shop.toLocalDomainShop(), this.shopLabels.toLocalDomainLabelList())
}

fun List<ShopWithAllLabel>.toDomainShopLabelList(): List<ShopLabel> {
    return this.map{
        ShopLabel(it.shop.toLocalDomainShop(), it.shopLabels.toLocalDomainLabelList())
    }
}