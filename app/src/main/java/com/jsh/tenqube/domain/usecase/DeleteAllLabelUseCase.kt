package com.jsh.tenqube.domain.usecase

import com.jsh.tenqube.domain.repository.LabelRepository

class DeleteAllLabelUseCase (
    private val defaultRepository: LabelRepository
){
    suspend operator fun invoke(){
        defaultRepository.deleteAllLabel()
    }
}