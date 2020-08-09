package com.jsh.tenqube.presentation.di

import com.jsh.tenqube.domain.repository.LabelRepository
import com.jsh.tenqube.domain.repository.ShopRepository
import com.jsh.tenqube.domain.usecase.GetLabelsUseCase
import com.jsh.tenqube.domain.usecase.GetShopsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class UseCaseModule {

    @Provides
    @ActivityRetainedScoped
    fun getLabelsUseCase(repo: LabelRepository): GetLabelsUseCase {
        return GetLabelsUseCase(repo)
    }


    @Provides
    @ActivityRetainedScoped
    fun getShopsUseCase(repo: ShopRepository): GetShopsUseCase {
        return GetShopsUseCase(repo)
    }
}