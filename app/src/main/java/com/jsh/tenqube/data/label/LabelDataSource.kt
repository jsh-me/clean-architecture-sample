package com.jsh.tenqube.data.label

import com.jsh.tenqube.data.db.LocalShopAndLabels
import com.jsh.tenqube.domain.Result
import com.jsh.tenqube.domain.entity.Label

interface LabelDataSource {

    suspend fun getLabels(): Result<List<Label>>

    suspend fun saveLabel(label: Label)

    suspend fun insertLabel(label: Label)

    suspend fun isLabelDBEmpty(): Boolean

    suspend fun deleteAllLabel()


//    suspend fun findLabelsByShopId(shopId: String): Result<List<Label>>
//
//    suspend fun findLabelsByShopName(shopName: String): Result<List<Label>>

}