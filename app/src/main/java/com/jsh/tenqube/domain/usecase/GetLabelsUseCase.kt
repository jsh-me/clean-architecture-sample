package com.jsh.tenqube.domain.usecase

import com.jsh.tenqube.domain.Result
import com.jsh.tenqube.domain.repository.LabelRepository

class GetLabelsUseCase(
    private val defaultRepository: LabelRepository
) {
    suspend operator fun invoke(isUpdated: Boolean = false): Result<MutableMap<String, String>> {
        return defaultRepository.getLabels(isUpdated)
    }
}