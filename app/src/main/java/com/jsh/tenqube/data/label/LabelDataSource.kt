package com.jsh.tenqube.data.label

import com.jsh.tenqube.domain.Result
import com.jsh.tenqube.domain.entity.DomainLabel

interface LabelDataSource {

    suspend fun getLabels(): Result<List<DomainLabel.Label>>

    suspend fun saveLabel(label: DomainLabel.Label)

    suspend fun insertLabel(label: DomainLabel.Label)

    suspend fun isLabelDBEmpty(): Boolean

    suspend fun deleteAllLabel()

}