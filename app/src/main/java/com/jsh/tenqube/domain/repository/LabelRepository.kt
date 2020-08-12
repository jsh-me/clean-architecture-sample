package com.jsh.tenqube.domain.repository

import com.jsh.tenqube.domain.Result
import com.jsh.tenqube.domain.entity.DomainLabel.Label


interface LabelRepository {

    suspend fun getLabels(): Result<List<Label>>

    suspend fun saveLabel(label: Label)

    suspend fun deleteAllLabel()

    suspend fun insertLabel(label: Label)
}