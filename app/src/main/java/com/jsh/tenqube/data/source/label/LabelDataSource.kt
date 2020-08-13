package com.jsh.tenqube.data.source.label

import com.jsh.tenqube.domain.util.Result
import com.jsh.tenqube.domain.entity.DomainLabel.*

interface LabelDataSource {

    suspend fun getLabels(): Result<List<Label>>

    suspend fun getLabel(id: String): Result<Label>

    suspend fun updateLabel(label: Label)

    suspend fun insertLabel(label: Label)

    suspend fun isLabelDBEmpty(): Boolean

    suspend fun deleteAllLabel()

}