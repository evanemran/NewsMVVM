package com.evanemran.newsmvvm.domain.repository

import com.evanemran.newsmvvm.domain.news.NewsData
import com.evanemran.newsmvvm.domain.util.Resource

interface NewsRepository {
    suspend fun getNewsData(country: String, category: String, query: String, apiKey: String): Resource<NewsData>
    suspend fun getNewsDataBySource(source: String, apiKey: String): Resource<NewsData>
}