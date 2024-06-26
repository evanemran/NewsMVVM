package com.evanemran.newsmvvm.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evanemran.newsmvvm.BuildConfig
import com.evanemran.newsmvvm.domain.repository.NewsRepository
import com.evanemran.newsmvvm.domain.util.Resource
import com.evanemran.newsmvvm.presentation.NewsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
): ViewModel() {
    var state by mutableStateOf(NewsState())
        private set

    fun loadNewsData(category: String, country: String, source: String, query: String) {
        viewModelScope.launch {
            state =state.copy(
                isLoading = true,
                error = null
            )

            when (val result = if (source == "N/A") newsRepository.getNewsData(country ,category, query, BuildConfig.API_KEY)
            else newsRepository.getNewsDataBySource(source,BuildConfig.API_KEY)) {
                is Resource.Success -> {
                    state = state.copy(
                        newsInfo = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        newsInfo = null,
                        isLoading = false,
                        error = result.message
                    )
                }

                else -> {
                    state = state.copy(
                        newsInfo = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }
}