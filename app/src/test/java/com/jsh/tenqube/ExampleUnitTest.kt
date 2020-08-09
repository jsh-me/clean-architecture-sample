package com.jsh.tenqube

import org.junit.Test
import org.junit.Assert.*


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
//
//    @Before
//    fun getRefreshOkHttp(): OkHttpClient {
//        val httpClient = OkHttpClient.Builder()
//        httpClient.addInterceptor { chain: Interceptor.Chain ->
//            var request = chain.request()
//                request = request.newBuilder()
//                    .method(request.method(), request.body())
//                    .addHeader("x-api-key", "12345678901234567890123456789012")
//                    .build()
//            chain.proceed(request)
//        }
//
//        //log
//        if (BuildConfig.DEBUG) {
//            val loggingInterceptor = HttpLoggingInterceptor()
//            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            httpClient.addInterceptor(loggingInterceptor)
//        }
//
//        // timeout
//        httpClient.readTimeout(1, TimeUnit.MINUTES)
//        httpClient.connectTimeout(30, TimeUnit.SECONDS)
//
//        return httpClient.build()
//    }
//

}