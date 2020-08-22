package com.jsh.practice.data.api

import com.jsh.practice.data.dto.LabelResponse
import com.jsh.practice.data.dto.ShopResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MyService {

    @GET("labels")
    suspend fun getLabels(): LabelResponse

    @GET("shops")
    suspend fun getShops(): ShopResponse

}