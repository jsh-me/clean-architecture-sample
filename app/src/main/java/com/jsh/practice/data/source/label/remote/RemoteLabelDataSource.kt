package com.jsh.practice.data.source.label.remote

import com.jsh.practice.data.api.MyService
import com.jsh.practice.data.source.label.LabelDataSource
import com.jsh.practice.data.mapper.toDomainLabelList
import com.jsh.practice.domain.util.Result
import com.jsh.practice.domain.entity.Label
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteLabelDataSource @Inject constructor(
    private val myService: MyService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): LabelDataSource {

    override suspend fun getLabels(): Result<List<Label>> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success((myService.getLabels().results).toDomainLabelList())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getLabel(id: String): Result<Label> = withContext(ioDispatcher) {
        Result.Error(Exception("UnSupported Operation"))
    }

    override suspend fun updateLabel(label: Label) {
        Result.Error(Exception("UnSupported Operation"))
    }

    override suspend fun insertLabel(label: Label) {
        Result.Error(Exception("UnSupported Operation"))
    }

    override suspend fun deleteAllLabel() {
        Result.Error(Exception("UnSupported Operation"))
    }
}