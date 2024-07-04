package com.example.holdings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holdings.models.PortfolioResponse
import com.example.holdings.network.repo.PortfolioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(private val repository: PortfolioRepository) : ViewModel() {
    private val _portfolio = MutableStateFlow<PortfolioResponse?>(null)
    val portfolio: StateFlow<PortfolioResponse?> get() = _portfolio

    init {
        viewModelScope.launch {
            _portfolio.value = repository.getPortfolio()
        }
    }
}
