package com.jsh.tenqube.data.label.remote

import com.jsh.tenqube.data.api.TenqubeService
import com.jsh.tenqube.data.label.LabelDataSource
import com.jsh.tenqube.data.mapper.toDomainLabelList
import com.jsh.tenqube.data.mapper.toDomainShopList
import com.jsh.tenqube.domain.Result
import com.jsh.tenqube.domain.entity.Label
import com.jsh.tenqube.domain.entity.Shop
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
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

    override suspend fun saveLabel(label: Label) {
        throw UnsupportedOperationException("unsupported operation")
    }

    override suspend fun insertLabel(label: Label) {
        throw UnsupportedOperationException("unsupported operation")
    }

    override suspend fun isLabelDBEmpty(): Boolean {
        throw UnsupportedOperationException("unsupported operation")
    }
}