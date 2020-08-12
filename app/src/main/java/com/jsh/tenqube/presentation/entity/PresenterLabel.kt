package com.jsh.tenqube.presentation.entity

sealed class PresenterLabelEntity {
    data class PresenterLabel(
        val id: String,
        val name: String
    ): PresenterLabelEntity()
}