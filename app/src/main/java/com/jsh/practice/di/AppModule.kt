package com.jsh.practice.di

import android.content.Context
import androidx.room.Room
import com.jsh.practice.data.api.MyService
import com.jsh.practice.data.source.shop.local.LocalShopDataSource
import com.jsh.practice.data.db.MyDatabase
import com.jsh.practice.data.source.label.LabelDataSource
import com.jsh.practice.data.source.label.LabelRepositoryImpl
import com.jsh.practice.data.source.label.local.LabelDao
import com.jsh.practice.data.source.label.local.LocalLabelDataSource
import com.jsh.practice.data.source.label.remote.RemoteLabelDataSource
import com.jsh.practice.data.source.shop.ShopDataSource
import com.jsh.practice.data.source.shop.ShopRepositoryImpl
import com.jsh.practice.data.source.shop.local.ShopDao
import com.jsh.practice.data.source.shop.remote.RemoteShopDataSource
import com.jsh.practice.domain.repository.LabelRepository
import com.jsh.practice.domain.repository.ShopRepository
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
    fun provideApiService(retrofit: RetrofitManager) = retrofit.getRetrofit().create(MyService::class.java)

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): MyDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MyDatabase::class.java,
            "practice.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    @remoteLabel
    fun provideRemoteLabelDataSource(
        service: MyService,
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
        service: MyService,
        labelRepository: LabelRepository,
        ioDispatcher: CoroutineDispatcher
    ): ShopDataSource {
        return RemoteShopDataSource(
            service, labelRepository, ioDispatcher
        )
    }

    @Singleton
    @Provides
    fun provideShopDao(db: MyDatabase): ShopDao = db.shopDao()

    @Singleton
    @Provides
    fun provideLabelDao(db: MyDatabase): LabelDao = db.labelDao()

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