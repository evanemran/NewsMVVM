package com.evanemran.newsmvvm.data.remote.dto

data class NewsDataDto(
    val articles: List<ArticleDto>,
    val status: String,
    val totalResults: Int
)