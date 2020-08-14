package com.jsh.tenqube.di

import android.content.Context
import androidx.room.Room
import com.jsh.tenqube.data.network.RetrofitManager
import com.jsh.tenqube.data.api.TenqubeService
import com.jsh.tenqube.data.source.shop.local.LocalShopDataSource
import com.jsh.tenqube.data.db.TenqubeDatabase
import com.jsh.tenqube.data.source.label.LabelDataSource
import com.jsh.tenqube.data.source.label.LabelRepositoryImpl
import com.jsh.tenqube.data.source.label.local.LabelDao
import com.jsh.tenqube.data.source.label.local.LocalLabelDataSource
import com.jsh.tenqube.data.source.label.remote.RemoteLabelDataSource
import com.jsh.tenqube.data.source.shop.ShopDataSource
import com.jsh.tenqube.data.source.shop.ShopRepositoryImpl
import com.jsh.tenqube.data.source.shop.local.ShopDao
import com.jsh.tenqube.data.source.shop.remote.RemoteShopDataSource
import com.jsh.tenqube.domain.repository.LabelRepository
import com.jsh.tenqube.domain.repository.ShopRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class remoteLabel

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class localLabel

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class remoteShop

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class localShop


    @Provides
    @Singleton
    fun provideApiService(retrofit: RetrofitManager) = retrofit.getRetrofit().create(TenqubeService::class.java)

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): TenqubeDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            TenqubeDatabase::class.java,
            "tenqube.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    @remoteLabel
    fun provideRemoteLabelDataSource(
        service: TenqubeService,
        ioDispatcher: CoroutineDispatcher
    ): LabelDataSource {
        return RemoteLabelDataSource(
            service, ioDispatcher
        )
    }

    @Singleton
    @Provides
    @localLabel
    fun provideLocalLabelDataSource(
        labelDao: LabelDao,
        ioDispatcher: CoroutineDispatcher
        ): LabelDataSource {
        return LocalLabelDataSource(
            labelDao, ioDispatcher
        )
    }

    @Singleton
    @Provides
    @remoteShop
    fun provideRemoteShopDataSource(
        service: TenqubeService,
        ioDispatcher: CoroutineDispatcher
    ): ShopDataSource {
        return RemoteShopDataSource(
            service, ioDispatcher
        )
    }

    @Singleton
    @Provides
    fun provideShopDao(db: TenqubeDatabase): ShopDao = db.shopDao()

    @Singleton
    @Provides
    fun provideLabelDao(db: TenqubeDatabase): LabelDao = db.labelDao()

    @Singleton
    @Provides
    @localShop
    fun provideLocalShopDataSource(
        shopDao: ShopDao,
        ioDispatcher: CoroutineDispatcher
    ): ShopDataSource {
        return LocalShopDataSource(
            shopDao, ioDispatcher
        )
    }

    @Singleton
    @Provides
    fun provideRetrofitService() = RetrofitManager


    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule{

    @Singleton
    @Provides
    fun provideShopRepository(
        @AppModule.remoteShop remoteDataSource: ShopDataSource,
        @AppModule.localShop localDataSource: ShopDataSource,
        ioDispatcher: CoroutineDispatcher
    ): ShopRepository {
        return ShopRepositoryImpl(remoteDataSource, localDataSource, ioDispatcher)
    }

    @Singleton
    @Provides
    fun provideLabelRepository(
        @AppModule.remoteLabel remoteDataSource: LabelDataSource,
        @AppModule.localLabel localDataSource: LabelDataSource,
        ioDispatcher: CoroutineDispatcher
    ): LabelRepository {
        return LabelRepositoryImpl(remoteDataSource, localDataSource, ioDispatcher)
    }
}