package com.evanemran.newsmvvm.domain.news

data class Articles(
    val source: String,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String
)
