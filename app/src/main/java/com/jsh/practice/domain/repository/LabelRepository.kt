package com.jsh.practice.domain.repository

import com.jsh.practice.domain.util.Result
import com.jsh.practice.domain.entity.Label

interface LabelRepository {

    suspend fun getLabels(isUpdated: Boolean): Result<List<Label>>

    suspend fun getLabel(id: String, isUpdated: Boolean): Result<Label>

    suspend fun updateLabel(label: Label)

    suspend fun deleteAllLabel()

    suspend fun insertLabel(label: Label)

}