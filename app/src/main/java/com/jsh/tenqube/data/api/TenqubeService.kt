package com.jsh.tenqube.data.api

import com.jsh.tenqube.data.dto.LabelResponse
import com.jsh.tenqube.data.dto.ShopResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TenqubeService {
    @GET("labels")
    suspend fun getLabels(): LabelResponse

    @GET("shops")
    suspend fun getShops(): ShopResponse

    @GET("shops/{id}")
    suspend fun getShops(@Query("id") id: String): ShopResponse
}