package com.jsh.tenqube.presentation.di

import android.content.Context
import androidx.room.Room
import com.jsh.tenqube.data.network.HttpClient
import com.jsh.tenqube.data.network.HttpClientNetwork
import com.jsh.tenqube.data.network.RetrofitManager
import com.jsh.tenqube.data.network.RetrofitManagerNetwork
import com.jsh.tenqube.data.api.TenqubeService
import com.jsh.tenqube.data.shop.local.LocalShopDataSource
import com.jsh.tenqube.data.db.TenqubeDatabase
import com.jsh.tenqube.data.label.LabelDataSource
import com.jsh.tenqube.data.label.LabelRepositoryImpl
import com.jsh.tenqube.data.label.local.LocalLabelDataSource
import com.jsh.tenqube.data.label.remote.RemoteLabelDataSource
import com.jsh.tenqube.data.shop.ShopDataSource
import com.jsh.tenqube.data.shop.ShopRepositoryImpl
import com.jsh.tenqube.data.shop.remote.RemoteShopDataSource
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
        ).build()
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
        database: TenqubeDatabase,
        ioDispatcher: CoroutineDispatcher
        ): LabelDataSource {
        return LocalLabelDataSource(
            database, ioDispatcher
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
    @localShop
    fun provideLocalShopDataSource(
        database: TenqubeDatabase,
        ioDispatcher: CoroutineDispatcher
    ): ShopDataSource {
        return LocalShopDataSource(
            database, ioDispatcher
        )
    }

    @Singleton
    @Provides
    fun provideHttpClientService(): HttpClient {
        return HttpClientNetwork()
    }

    @Singleton
    @Provides
    fun provideRetrofitService(okHttp: HttpClient): RetrofitManager {
        return RetrofitManagerNetwork(okHttp)
    }

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