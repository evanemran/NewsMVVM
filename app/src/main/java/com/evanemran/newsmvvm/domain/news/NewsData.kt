package com.evanemran.newsmvvm.domain.news

data class NewsData(
    val articles: List<Articles>,
    val totalResult: Int
)