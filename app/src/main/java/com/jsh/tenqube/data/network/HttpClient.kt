package com.jsh.tenqube.data.network

import okhttp3.OkHttpClient

interface HttpClient {
    fun getOkHttp(): OkHttpClient
}