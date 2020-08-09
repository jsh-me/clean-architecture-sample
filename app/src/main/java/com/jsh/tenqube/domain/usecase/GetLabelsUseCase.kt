package com.jsh.tenqube.domain.usecase

import com.jsh.tenqube.domain.Result
import com.jsh.tenqube.domain.repository.LabelRepository
import java.lang.Exception

class GetLabelsUseCase(
    private val defaultRepository: LabelRepository
) {
    suspend operator fun invoke(): Result<MutableMap<String, String>> {
        defaultRepository.getLabels().let { result ->
            if (result is Result.Success) {
                result.data.map {
                    return Result.Success(mutableMapOf(Pair(it.id, it.name)))
                }
            }
        }
        return Result.Error(Exception("Mapping Error"))
    }
}