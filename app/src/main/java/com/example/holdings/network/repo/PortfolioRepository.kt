package com.example.holdings.network.repo

import com.example.holdings.models.PortfolioResponse
import com.example.holdings.network.source.PortfolioService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PortfolioRepository @Inject constructor(private val service: PortfolioService) {
    suspend fun getPortfolio(): PortfolioResponse = service.getPortfolio()
}
