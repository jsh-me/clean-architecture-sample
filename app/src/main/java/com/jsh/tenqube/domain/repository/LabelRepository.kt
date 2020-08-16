package com.jsh.tenqube.domain.repository

import com.jsh.tenqube.domain.util.Result
import com.jsh.tenqube.domain.entity.DomainLabel.Label


interface LabelRepository {

    suspend fun getLabels(isUpdated: Boolean): Result<List<Label>>

    suspend fun getLabel(id: String, isUpdated: Boolean): Result<Label>

    suspend fun updateLabel(label: Label)

    suspend fun deleteAllLabel()

    suspend fun insertLabel(label: Label)
}