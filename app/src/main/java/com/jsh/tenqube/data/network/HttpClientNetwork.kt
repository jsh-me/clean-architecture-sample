package com.jsh.tenqube.data.network

import com.jsh.tenqube.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class HttpClientNetwork:
    HttpClient {
    override fun getOkHttp(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain: Interceptor.Chain ->
            var request = chain.request()
            request = request.newBuilder()
                .method(request.method(), request.body())
                .addHeader("x-api-key", "12345678901234567890123456789012")
                .build()
            chain.proceed(request)
        }

        //log
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(loggingInterceptor)
        }

        // timeout
        httpClient.readTimeout(1, TimeUnit.MINUTES)
        httpClient.connectTimeout(30, TimeUnit.SECONDS)

        return httpClient.build()
    }
}