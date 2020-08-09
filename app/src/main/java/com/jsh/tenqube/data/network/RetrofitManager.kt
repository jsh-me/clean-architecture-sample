package com.jsh.tenqube.data.network

import retrofit2.Retrofit

interface RetrofitManager {
    fun getRetrofit(): Retrofit
}