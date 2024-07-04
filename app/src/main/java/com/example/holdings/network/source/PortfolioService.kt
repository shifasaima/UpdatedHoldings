package com.example.holdings.network.source

import com.example.holdings.models.PortfolioResponse
import retrofit2.http.GET

interface PortfolioService {
    @GET("/")
    suspend fun getPortfolio(): PortfolioResponse
}
