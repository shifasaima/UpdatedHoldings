package com.example.holdings.di

import com.example.holdings.network.source.PortfolioService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://35dee773a9ec441e9f38d5fc249406ce.api.mockbin.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providePortfolioService(retrofit: Retrofit): PortfolioService =
        retrofit.create(PortfolioService::class.java)
}
