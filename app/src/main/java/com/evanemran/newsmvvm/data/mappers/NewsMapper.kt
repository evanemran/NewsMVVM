package com.evanemran.newsmvvm.data.mappers

import com.evanemran.newsmvvm.data.remote.dto.NewsDataDto
import com.evanemran.newsmvvm.domain.news.Articles
import com.evanemran.newsmvvm.domain.news.NewsData

fun NewsDataDto.toNewsData() : NewsData {
    return NewsData(
        articles = articles.mapIndexed { index, articleDto ->
                                       val source = articles[index].source.name
            val author = articles[index].author
            val title = articles[index].title
            val description = articles[index].description
            val url = articles[index].url
            val urlToImage = articles[index].urlToImage
            val publishedAt = articles[index].publishedAt

            Articles(
                source = source,
                author = author,
                title = title,
                description = description,
                url = url,
                urlToImage = urlToImage,
                publishedAt = publishedAt
            )
        }.toList(),
        totalResult = totalResults
    )
}