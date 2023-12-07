package com.evanemran.newsmvvm.presentation.utils

import com.evanemran.newsmvvm.domain.news.Category
import com.evanemran.newsmvvm.presentation.Country

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

fun getCountries(): List<Country> {
    return listOf(
        Country("United Arab Emirates","https://flagsapi.com/AE/flat/64.png","ae"),
        Country("Argentina","","ar"),
        Country("Austria","","at"),
        Country("Australia","","au"),
        Country("Belgium","","be"),
        Country("Bulgaria","","bg"),
        Country("Brazil","","br"),
        Country("Canada","","ca"),
        Country("Switzerland","","ch"),
        Country("China","","cn"),
        Country("Colombia","","co"),
        Country("Cuba","","cu"),
        Country("Czechia","","cz"),
        Country("Germany","","de"),
        Country("Egypt","","eg"),
        Country("France","","fr"),
        Country("Great Britain","","gb"),
    )
}