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

        Country("Greece","","gr"),
        Country("Hong Kong", "", "hk"),
        Country("Hungary", "", "hu"),
        Country("Indonesia", "", "id"),
        Country("Ireland", "", "ie"),
        Country("Israel", "", "il"),
        Country("India", "", "in"),
        Country("Italy", "", "it"),
        Country("Japan", "", "jp"),
        Country("South Korea", "", "kr"),
        Country("Lithuania", "", "lt"),
        Country("Luxembourg", "", "lu"),
        Country("Morocco", "", "ma"),
        Country("Mexico", "", "mx"),
        Country("Malaysia", "", "my"),
        Country("Nigeria", "", "ng"),
        Country("Netherlands", "", "nl"),
        Country("New Zealand", "", "nz"),
        Country("Philippines", "", "ph"),
        Country("Poland", "", "pl"),
        Country("Portugal", "", "pt"),
        Country("Romania", "", "ro"),
        Country("Serbia", "", "rs"),
        Country("Russia", "", "ru"),
        Country("Saudi Arabia", "", "sa"),
        Country("Sweden", "", "se"),
        Country("Singapore", "", "sg"),
        Country("Slovakia", "", "sk"),
        Country("Thailand", "", "th"),
        Country("Turkey", "", "tr"),
        Country("Taiwan", "", "tw"),
        Country("Ukraine", "", "ua"),
        Country("United States", "", "us"),
        Country("Venezuela", "", "ve"),
        Country("South Africa", "", "za")
    )
}