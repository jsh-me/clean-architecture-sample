package com.jsh.tenqube.data.db

import androidx.room.Embedded
import androidx.room.Relation
import com.jsh.tenqube.data.label.local.LocalLabelModel
import com.jsh.tenqube.data.shop.local.LocalShopModel

data class ShopAndAllLabels (

    @Embedded
    var shop: LocalShopModel,

    @Relation(parentColumn = "labels",
                entityColumn = "id")
    var labels: List<LocalLabelModel>

)