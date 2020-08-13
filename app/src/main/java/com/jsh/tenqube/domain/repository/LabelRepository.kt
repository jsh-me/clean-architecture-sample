package com.jsh.tenqube.domain.repository

import com.jsh.tenqube.domain.util.Result
import com.jsh.tenqube.domain.entity.DomainLabel.Label


interface LabelRepository {

    suspend fun getLabels(): Result<List<Label>>

    suspend fun fetchListFromRemoteOrLocal(id: String): Result<Label>

    suspend fun saveLabel(label: Label)

    suspend fun deleteAllLabel()

    suspend fun insertLabel(label: Label)
}