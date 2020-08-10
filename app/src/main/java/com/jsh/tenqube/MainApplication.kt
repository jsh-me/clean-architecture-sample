package com.jsh.tenqube

import android.app.Application
import com.amitshekhar.DebugDB.*
import com.jsh.tenqube.domain.usecase.DeleteAllShopUseCase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application(){


    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        Timber.e("my IP : ${getAddressLog()}")

    }
}