package com.jsh.practice.data.source.label

import com.jsh.practice.domain.entity.Label
import com.jsh.practice.domain.util.Result

interface LabelDataSource {

    suspend fun getLabels(): Result<List<Label>>

    suspend fun getLabel(id: String): Result<Label>

    suspend fun updateLabel(label: Label)

    suspend fun insertLabel(label: Label)

    suspend fun deleteAllLabel()

}