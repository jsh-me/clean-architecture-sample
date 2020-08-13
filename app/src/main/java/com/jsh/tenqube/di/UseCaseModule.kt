package com.jsh.tenqube.di

import com.jsh.tenqube.domain.repository.LabelRepository
import com.jsh.tenqube.domain.repository.ShopLabelRepository
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
    fun getLabelsUseCase(repo: LabelRepository): GetLabelsUseCase {
        return GetLabelsUseCase(repo)
    }


    @Provides
    @ActivityRetainedScoped
    fun getShopsUseCase(repo: ShopRepository): GetShopsUseCase {
        return GetShopsUseCase(repo)
    }

    @Provides
    @ActivityRetainedScoped
    fun deleteAllShopUseCase(shop: ShopRepository, shopLabel: ShopLabelRepository): DeleteAllShopUseCase {
        return DeleteAllShopUseCase(shop, shopLabel)
    }

    @Provides
    @ActivityRetainedScoped
    fun getShopWithLabelsUseCase(repo: ShopLabelRepository): GetShopWithLabelsUseCase {
        return GetShopWithLabelsUseCase(repo)
    }

    @Provides
    @ActivityRetainedScoped
    fun deleteAllLabelUseCase(repo: LabelRepository): DeleteAllLabelUseCase {
        return DeleteAllLabelUseCase(repo)
    }

    @Provides
    @ActivityRetainedScoped
    fun updateShopInfoUseCase(repo: ShopRepository): UpdateShopInfoUseCase {
        return UpdateShopInfoUseCase(repo)
    }

    @Provides
    @ActivityRetainedScoped
    fun deleteShopInfoUseCase(repo: ShopRepository): DeleteShopInfoUseCase {
        return DeleteShopInfoUseCase(repo)
    }

    @Provides
    @ActivityRetainedScoped
    fun insertShopInfoUseCase(shop: ShopRepository, shopLabel: ShopLabelRepository): InsertShopInfoUseCase {
        return InsertShopInfoUseCase(shop, shopLabel)
    }

    @Provides
    @ActivityRetainedScoped
    fun insertLabelInfoUseCase(repo: LabelRepository): InsertLabelInfoUseCase {
        return InsertLabelInfoUseCase(repo)
    }

    @Provides
    @ActivityRetainedScoped
    fun insertShopLabelUseCase(repo: ShopLabelRepository): InsertShopLabelUseCase {
        return InsertShopLabelUseCase(repo)
    }


}