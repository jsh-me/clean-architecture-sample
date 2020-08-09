package com.jsh.tenqube.data.label

import com.jsh.tenqube.domain.entity.Label
import com.jsh.tenqube.domain.repository.LabelRepository
import com.jsh.tenqube.domain.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LabelRepositoryImpl @Inject constructor(
    private val remoteDataSource: LabelDataSource,
    private val localDataSource: LabelDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): LabelRepository {
    private var cachedLabelList = mutableMapOf<String, String>()

    override suspend fun getLabels(isUpdated: Boolean): Result<MutableMap<String, String>> =
        withContext(ioDispatcher) {
            remoteDataSource.getLabels().let { result ->
                if (result is Result.Success) {
                    result.data.map { label ->
                        cacheList(label)
                    }
                }
                return@withContext Result.Success(cachedLabelList)
            }

            //  val newShopsAndLabels = fetchListFromRemoteOrLocal(isUpdated)
        }

    private suspend fun fetchListFromRemoteOrLocal(isUpdated: Boolean){
        val remoteLabelData = remoteDataSource.getLabels()

        if (remoteLabelData is Result.Success) {
            refreshLocalDataSource(remoteLabelData.data)
        }
    }

    override suspend fun saveLabel(label: Label) {
        TODO("Not yet implemented")
    }

    private suspend fun refreshLocalDataSource(labelList: List<Label>) {

        for (label in labelList) {
            localDataSource.saveLabel(label)
        }
    }


    private fun cacheList(list: Any) {
        if (list is Label) {
            cachedLabelList[list.id] = list.name
        }
    }
}