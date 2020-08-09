package com.jsh.tenqube.domain.repository

import com.jsh.tenqube.domain.entity.Shop
import com.jsh.tenqube.domain.Result
import com.jsh.tenqube.domain.entity.Label

interface LabelRepository {

    suspend fun getLabels(isUpdated: Boolean = false): Result<MutableMap<String, String>>

    suspend fun saveLabel(label: Label)

}