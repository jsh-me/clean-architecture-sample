package com.jsh.tenqube.domain.repository

import com.jsh.tenqube.data.label.local.LocalLabelModel
import com.jsh.tenqube.domain.entity.Shop
import com.jsh.tenqube.domain.Result
import com.jsh.tenqube.domain.entity.Label

interface LabelRepository {

    suspend fun getLabels(): Result<List<Label>>

    suspend fun saveLabel(label: Label)

//    suspend fun findLabelsByShopName(shopName: String): Result<List<Label>>
//
//    suspend fun findLabelsByShopId(shopId: String): Result<List<Label>>
}