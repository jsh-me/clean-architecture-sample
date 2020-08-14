package com.jsh.tenqube.di

import com.jsh.tenqube.domain.repository.LabelRepository
import com.jsh.tenqube.domain.repository.ShopRepository
import com.jsh.tenqube.domain.usecase.*
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
    fun getShopsUseCase(shop: ShopRepository, label: LabelRepository): GetShopsUseCase {
        return GetShopsUseCase(shop, label)
    }

    @Provides
    @ActivityRetainedScoped
    fun deleteAllShopUseCase(shop: ShopRepository): DeleteAllShopUseCase {
        return DeleteAllShopUseCase(shop)
    }
    
    @Provides
    @ActivityRetainedScoped
    fun updateShopUseCase(repo: ShopRepository): UpdateShopUseCase {
        return UpdateShopUseCase(repo)
    }

    @Provides
    @ActivityRetainedScoped
    fun deleteShopUseCase(repo: ShopRepository): DeleteShopUseCase {
        return DeleteShopUseCase(repo)
    }

    @Provides
    @ActivityRetainedScoped
    fun insertShopUseCase(shop: ShopRepository, label: LabelRepository): InsertShopUseCase {
        return InsertShopUseCase(shop, label)
    }
}