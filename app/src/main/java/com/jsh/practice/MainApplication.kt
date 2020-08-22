package com.jsh.practice

import android.app.Application
import com.amitshekhar.DebugDB.*
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        Timber.e("my IP : ${getAddressLog()}")
    }
}