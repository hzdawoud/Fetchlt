package com.hzdawoud.fetchlt.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hzdawoud.fetchlt.data.repository.EodDataRepository
import com.hzdawoud.fetchlt.domain.model.EodEntry
import com.hzdawoud.fetchlt.utils.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EodDataViewModel @Inject constructor(
    private val repository: EodDataRepository
) : ViewModel() {

    private val _stockListState = MutableStateFlow(StockListUiState(isLoading = true))
    val stockListState: StateFlow<StockListUiState> = _stockListState.asStateFlow()

    private val _stockDetailState = MutableStateFlow(StockDetailUiState(isLoading = false))
    val stockDetailState: StateFlow<StockDetailUiState> = _stockDetailState.asStateFlow()

    // Cache the stocks for detail screen
    private var stocksCache: List<EodEntry> = emptyList()

    // Fetch stock data for the list
    fun fetchStocks(symbols: String) {
        viewModelScope.launch {
            _stockListState.value = StockListUiState(isLoading = true)

            when (val result = repository.getEndOfDayData(symbols)) {
                is Resource.Success -> {
                    stocksCache = result.data.data
                    _stockListState.value = StockListUiState(stocks = result.data.data)
                }

                is Resource.Error -> {
                    _stockListState.value = StockListUiState(
                        error = result.message ?: "Unknown error occurred"
                    )
                }

                Resource.Loading -> {
                    // This is handled by setting isLoading = true above
                    Log.d(TAG, "fetchStocks: Loading state")
                }
            }
        }
    }

    // Load details for a specific stock uuid
    fun loadStockDetails(id: String) {
        viewModelScope.launch {
            _stockDetailState.value = StockDetailUiState(isLoading = true)

            // Find the stock in our cache
            val stock = stocksCache.find { it.id == id }

            if (stock != null) {
                _stockDetailState.value = StockDetailUiState(stock = stock)
            } else {
                _stockDetailState.value = StockDetailUiState(
                    error = "Stock details not found"
                )
            }
        }
    }

    fun readStockSymbolsFromFile(context: Context): String {
        return try {
            context.assets.open("tickers.txt").bufferedReader().use { it.readText() }.trim()
        } catch (e: Exception) {
            Log.e("EodListScreen", "Error reading stock symbols file: ${e.message}")
            // Fallback to default symbols if file reading fails
            "AAPL,MSFT,GOOG,AMZN"
        }
    }

    companion object {
        val TAG: String = EodDataViewModel::class.java.simpleName
    }
}

data class StockListUiState(
    val stocks: List<EodEntry> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

// UI state for stock details
data class StockDetailUiState(
    val stock: EodEntry? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)