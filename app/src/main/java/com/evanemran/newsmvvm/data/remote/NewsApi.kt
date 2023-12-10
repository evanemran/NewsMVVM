package com.evanemran.newsmvvm.data.remote

import com.evanemran.newsmvvm.data.remote.dto.NewsDataDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getHeadlines(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("q") query: String,
        @Query("apiKey") apiKey: String,
    ): NewsDataDto

    @GET("v2/top-headlines")
    suspend fun getHeadlinesBySource(
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String,
    ): NewsDataDto
}