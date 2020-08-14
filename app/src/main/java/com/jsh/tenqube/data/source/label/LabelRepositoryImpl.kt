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
            if(isUpdated) {
                cachedLabels?.let { cachedLabels ->
                    return@withContext Success(cachedLabels.values.sortedBy { it.id })
                }
            }

            val newLabels = fetchLabels(isUpdated)
            (newLabels as? Success)?.let { refreshCache(it.data) }

            cachedLabels?.values?.let{labels ->
                return@withContext Success(labels.sortedBy { it.id })
            }

            (newLabels as? Success)?.let{
                if(it.data.isEmpty()){
                    return@withContext Success(it.data)
                }
            }
            return@withContext Error(Exception("Illegal State"))
        }
    }

    private suspend fun fetchLabels(id: String): Result<Label> {
        val remoteLabelData = remoteDataSource.getLabel(id)

        when(remoteLabelData){
            is Error -> Timber.w("Remote data source fetch failed")
            is Success ->{
                refreshLocalDataSource(remoteLabelData.data)
                return remoteLabelData
            }
            else ->  throw IllegalStateException()
        }
        val localLabelData = localDataSource.getLabel(id)
        if( localLabelData is Success) return localLabelData
        return Error(Exception("Error fetching from remote and local"))
    }

    override suspend fun insertLabel(label: Label) {
        coroutineScope {
            launch { localDataSource.insertLabel(label) }
            launch { remoteDataSource.insertLabel(label) }
        }
        cachedLabels?.put(label.id, label)
    }

    private suspend fun fetchLabels(isUpdated: Boolean): Result<List<Label>> {
        val remoteLabelData = remoteDataSource.getLabels()

        when ( remoteLabelData ) {
            is Error -> Timber.w("Remote data source fetch failed")
            is Success -> {
                refreshLocalDataSource(remoteLabelData.data)
                return remoteLabelData
            }
            else -> throw IllegalStateException()
        }

        if (isUpdated) {
            return Error(Exception("Can't force refresh: remote data source is unavailable"))
        }

        val localLabelData = localDataSource.getLabels()
        if( localLabelData is Success) return localLabelData
        return Error(Exception("Error fetching from remote and local"))
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