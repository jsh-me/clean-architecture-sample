package com.jsh.tenqube.data.source.label

import com.jsh.tenqube.domain.entity.Label
import com.jsh.tenqube.domain.util.Result

interface LabelDataSource {

    suspend fun getLabels(): Result<List<Label>>

    suspend fun getLabel(id: String): Result<Label>

    suspend fun updateLabel(label: Label)

    suspend fun insertLabel(label: Label)

    suspend fun deleteAllLabel()

}