package com.jsh.tenqube.data.label

import com.jsh.tenqube.domain.entity.Label
import com.jsh.tenqube.domain.repository.LabelRepository
import com.jsh.tenqube.domain.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class LabelRepositoryImpl @Inject constructor(
    private val remoteDataSource: LabelDataSource,
    private val localDataSource: LabelDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): LabelRepository {

//    override suspend fun getLabels(): Result<MutableMap<String, String>> =
//        withContext(ioDispatcher) {
//            if(localDataSource.isLabelDBEmpty()){
//            remoteDataSource.getLabels().let { result ->
//                if (result is Result.Success) {
//                    result.data.map { label ->
//                        cacheList(label)
//                    }
//                }
//                return@withContext Result.Success(cachedLabelList)
//            }
//            }
//
//            //  val newShopsAndLabels = fetchListFromRemoteOrLocal(isUpdated)
//        }

    override suspend fun getLabels(): Result<List<Label>> = withContext(ioDispatcher) {
        if(localDataSource.isLabelDBEmpty()){ //room db 갯수 여부, 비어있으면 remote 로 불러오기
            Timber.e("remoteDataSource label available")
           remoteDataSource.getLabels().let{ result ->
               if(result is Result.Success){
                   cacheLabel(result.data) //remote data를 room에 insert 하기!

                   return@withContext Result.Success(result.data)
               } else Result.Error(Exception("Remote Illegal state"))
           }
        } else {
            Timber.e("localDataSource label available")
            localDataSource.getLabels().let{ result ->
                if(result is Result.Success){
                    return@withContext Result.Success(result.data)
                } else Result.Error(Exception("Local Illegal state"))
            }
        }
    }

    private suspend fun fetchListFromRemoteOrLocal(){
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


    private suspend fun cacheLabel(list: List<Label>) = withContext(ioDispatcher) {
        list.map{
            localDataSource.insertLabel(it)
        }
    }
}