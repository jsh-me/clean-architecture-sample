package com.jsh.tenqube.data.network

import android.annotation.SuppressLint
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jsh.tenqube.data.Consts.baseurl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManagerNetwork(private val okHttpRepo: HttpClient):
    RetrofitManager {
    @SuppressLint("DefaultLocale")
    override fun getRetrofit(): Retrofit {
        val client = okHttpRepo.getOkHttp()
        return Retrofit.Builder()
            .baseUrl(baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
    }
}
