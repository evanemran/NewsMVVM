package com.evanemran.newsmvvm.data.repository

import com.evanemran.newsmvvm.data.mappers.toNewsData
import com.evanemran.newsmvvm.data.remote.NewsApi
import com.evanemran.newsmvvm.domain.news.NewsData
import com.evanemran.newsmvvm.domain.repository.NewsRepository
import com.evanemran.newsmvvm.domain.util.Resource
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi
): NewsRepository {
    override suspend fun getNewsData(
        country: String,
        category: String,
        apiKey: String
    ): Resource<NewsData> {
        return try {
            Resource.Success(
                data =api.getHeadlines(
                    country = country, category = category, apiKey = apiKey
                ).toNewsData()
            )
        }
        catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred!")
        }
    }
}