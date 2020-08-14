package com.jsh.tenqube.data.source.label.remote

import com.jsh.tenqube.data.api.TenqubeService
import com.jsh.tenqube.data.source.label.LabelDataSource
import com.jsh.tenqube.data.mapper.toDomainLabelList
import com.jsh.tenqube.domain.util.Result
import com.jsh.tenqube.domain.entity.DomainLabel.*
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

    private var tenqubeServiceData: ConcurrentMap<String, Label>?= null

    override suspend fun getLabels(): Result<List<Label>> = withContext(ioDispatcher) {
        return@withContext try{
            cacheLabels(tenqubeService.getLabels().results.toDomainLabelList())
            Result.Success((tenqubeService.getLabels().results).toDomainLabelList())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getLabel(id: String): Result<Label> = withContext(ioDispatcher) {
        return@withContext try{
            Result.Success(tenqubeServiceData?.get(id)!!)
        } catch (e: java.lang.Exception) {
            Result.Error(e)
        }
    }

    override suspend fun updateLabel(label: Label) {
        tenqubeServiceData?.set(label.id, label)
    }

    override suspend fun insertLabel(label: Label) {
        tenqubeServiceData?.put(label.id, label)
    }

    override suspend fun deleteAllLabel() {
        tenqubeServiceData?.clear()
    }

    private fun cacheLabels(results: List<Label>){
        if(tenqubeServiceData == null){
            tenqubeServiceData = ConcurrentHashMap()
        }
        results.map{
            tenqubeServiceData?.put(it.id, it)
        }
    }


}