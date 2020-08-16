package com.jsh.tenqube.data.source.label

import com.jsh.tenqube.domain.repository.LabelRepository
import com.jsh.tenqube.domain.util.Result
import com.jsh.tenqube.domain.entity.DomainLabel.*
import com.jsh.tenqube.domain.entity.DomainShop
import com.jsh.tenqube.domain.util.Result.*
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import javax.inject.Inject

class LabelRepositoryImpl @Inject constructor(
    private val remoteDataSource: LabelDataSource,
    private val localDataSource: LabelDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): LabelRepository {

    private var cachedLabels: ConcurrentMap<String, Label> ?= null

    override suspend fun getLabels(isUpdated: Boolean): Result<List<Label>> {
        return withContext(ioDispatcher) {
            if(!isUpdated) {
                cachedLabels?.let { cachedLabels ->
                    return@withContext Success(cachedLabels.values.sortedBy { it.id })
                }
            }

            val newLabels = fetchLabels(isUpdated)
            (newLabels as? Success)?.let { refreshCache(it.data) }

            cachedLabels?.values?.let{labels ->
                return@withContext Success(labels.sortedBy { it.id })
            }
            return@withContext Error(Exception("Illegal State"))
        }
    }

    override suspend fun getLabel(id: String, isUpdated: Boolean): Result<Label> {
        return withContext(ioDispatcher){
            if(!isUpdated){
                getLabelById(id)?.let{
                    return@withContext Success(it)
                }
            }
            val newLabel = fetchLabel(id, isUpdated)
            (newLabel as? Success)?.let{
                cacheLabel(it.data)
                return@withContext Success(getLabelById(id)!!)
            }
            return@withContext Error(Exception("Error fetching from remote and local"))
        }
    }

    private suspend fun fetchLabel(id: String, isUpdated: Boolean): Result<Label> {
        val localLabelData = localDataSource.getLabel(id)
        if( localLabelData is Success) return localLabelData

        val remoteLabelData = remoteDataSource.getLabel(id)

        when(remoteLabelData){
            is Error -> Timber.w("Remote data source fetch failed")
            is Success ->{
                refreshLocalDataSource(remoteLabelData.data)
                return remoteLabelData
            }
            else ->  throw IllegalStateException()
        }

        if (isUpdated) {
            return Error(Exception("Refresh failed"))
        }
        return Error(Exception("Error fetching from remote and local"))
    }

    override suspend fun insertLabel(label: Label) {
        coroutineScope {
            launch { localDataSource.insertLabel(label) }
            launch { remoteDataSource.insertLabel(label) }
        }
        cachedLabels?.put(label.id, label)
    }

    private suspend fun fetchLabels(isUpdated: Boolean): Result<List<Label>> = withContext(ioDispatcher) {
        val localLabelData = localDataSource.getLabels()
        (localLabelData as? Success)?.let {
            if(it.data.isNotEmpty())
                return@withContext localLabelData //로컬에 데이터가 있으면 로컬 데이터를 반환
        }

        val remoteLabelData = remoteDataSource.getLabels()

        when ( remoteLabelData ) {
            is Error -> Timber.w("Remote data source fetch failed")
            is Success -> {
                refreshLocalDataSource(remoteLabelData.data)
                return@withContext remoteLabelData
            }
            else -> throw IllegalStateException()
        }

        if (isUpdated) {
            return@withContext Error(Exception("Can't force refresh: remote data source is unavailable"))
        }

        return@withContext Error(Exception("Error fetching from remote and local"))
    }

    override suspend fun updateLabel(label: Label) {
        coroutineScope {
            launch { localDataSource.updateLabel(label) }
            launch { remoteDataSource.updateLabel(label) }
        }
    }


    override suspend fun deleteAllLabel() {
        coroutineScope {
            launch { localDataSource.deleteAllLabel() }
            launch { remoteDataSource.deleteAllLabel() }
        }
        cachedLabels?.clear()
    }

    private suspend fun refreshLocalDataSource(labelList: List<Label>) {
        localDataSource.deleteAllLabel()
        for (label in labelList) {
            localDataSource.insertLabel(label)
        }
    }

    private suspend fun refreshLocalDataSource(label: Label) {
        localDataSource.insertLabel(label)
    }

    private fun getLabelById(id: String) = cachedLabels?.get(id)

    private fun refreshCache(labels: List<Label>){
        cachedLabels?.clear()
        labels.sortedBy { it.id }.forEach {
            cacheAndPerform(it) {}
        }
    }

    private fun cacheLabel(label: Label): Label{
        val cachedLabel = Label(label.id, label.name)
        if(cachedLabels == null){
            cachedLabels = ConcurrentHashMap()
        }
        cachedLabels?.put(cachedLabel.id, cachedLabel)
        return cachedLabel
    }

    private inline fun cacheAndPerform(label: Label, perform: (Label) -> Unit) {
        val cacheLabel = cacheLabel(label)
        perform(cacheLabel)
    }
}