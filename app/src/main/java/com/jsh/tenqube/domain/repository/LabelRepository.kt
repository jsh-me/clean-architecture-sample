package com.jsh.tenqube.domain.repository

import com.jsh.tenqube.domain.entity.Shop
import com.jsh.tenqube.domain.Result
import com.jsh.tenqube.domain.entity.Label

interface LabelRepository {

    suspend fun getLabels(): Result<List<Label>>

    suspend fun saveLabel(label: Label)

}