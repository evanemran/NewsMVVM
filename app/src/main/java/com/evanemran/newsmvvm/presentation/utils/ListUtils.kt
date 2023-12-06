package com.evanemran.newsmvvm.presentation.utils

import com.evanemran.newsmvvm.domain.news.Category

fun getCategories(): List<Category> {
    return listOf(
        Category.GENERAL,
        Category.BUSINESS,
        Category.SPORTS,
        Category.ENTERTAINMENT,
        Category.HEALTH,
        Category.SCIENCE,
        Category.TECHNOLOGY,
    )
}