package com.evanemran.newsmvvm.di

import com.evanemran.newsmvvm.data.repository.NewsRepositoryImpl
import com.evanemran.newsmvvm.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository
}