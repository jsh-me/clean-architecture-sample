package com.jsh.tenqube.domain.usecase

import com.jsh.tenqube.domain.entity.DomainLabel
import com.jsh.tenqube.domain.entity.DomainLabel.*
import com.jsh.tenqube.domain.repository.LabelRepository
import timber.log.Timber

class InsertLabelInfoUseCase(
    private val defaultRepository: LabelRepository
){
    suspend operator fun invoke(label: Label){
        Timber.e("Label insert success")
        defaultRepository.insertLabel(label)
    }
}