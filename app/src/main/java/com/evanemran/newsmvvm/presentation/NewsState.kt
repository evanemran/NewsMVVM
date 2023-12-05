package com.evanemran.newsmvvm.presentation

import com.evanemran.newsmvvm.domain.news.NewsData

data class NewsState(
    val newsInfo: NewsData? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)