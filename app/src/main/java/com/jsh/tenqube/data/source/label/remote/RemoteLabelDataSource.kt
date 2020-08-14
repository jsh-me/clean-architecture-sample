package com.jsh.tenqube.data.source.label.remote

import com.jsh.tenqube.data.api.TenqubeService
import com.jsh.tenqube.data.source.label.LabelDataSource
import com.jsh.tenqube.data.mapper.toDomainLabelList
import com.jsh.tenqube.domain.util.Result
import com.jsh.tenqube.domain.entity.DomainLabel.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class RemoteLabelDataSource @Inject constructor(
    private val tenqubeService: TenqubeService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): LabelDataSource {


    override suspend fun getLabels(): Result<List<Label>> = withContext(ioDispatcher) {
        return@withContext try{
            Result.Success((tenqubeService.getLabels().results).toDomainLabelList())
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