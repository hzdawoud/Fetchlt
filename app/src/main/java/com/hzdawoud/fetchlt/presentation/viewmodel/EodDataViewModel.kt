package com.hzdawoud.fetchlt.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hzdawoud.fetchlt.data.repository.EodDataRepository
import com.hzdawoud.fetchlt.domain.model.EodEntry
import com.hzdawoud.fetchlt.utils.network.Either
import com.hzdawoud.fetchlt.utils.network.ErrorEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EodDataViewModel @Inject constructor(
    private val repository: EodDataRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _stockListState = MutableStateFlow<StockListUiState>(StockListUiState.Loading)
    val stockListState: StateFlow<StockListUiState> = _stockListState.asStateFlow()

    private val _stockDetailState = MutableStateFlow<StockDetailUiState>(StockDetailUiState.Loading)
    val stockDetailState: StateFlow<StockDetailUiState> = _stockDetailState.asStateFlow()

    // Cache the stocks for detail screen
    private var stocksCache: List<EodEntry> = emptyList()

    // Fetch stock data for the list
    fun fetchStocks(symbols: String) {
        viewModelScope.launch {
            _stockListState.value = StockListUiState.Loading

            val result = withContext(dispatcher) {
                repository.getEndOfDayData(symbols)
            }

            _stockListState.value = when (result) {
                is Either.Success -> StockListUiState.Success(result.data)
                is Either.Error -> StockListUiState.Error(result.error)
            }.also {
                if (it is StockListUiState.Success) {
                    stocksCache = it.stocks
                }
            }
        }
    }

    // Load details for a specific stock uuid
    fun loadStockDetails(id: String) {
        viewModelScope.launch {
            _stockDetailState.value = StockDetailUiState.Loading

            // Find the stock in our cache
            val stock = stocksCache.firstOrNull { it.id == id }
            _stockDetailState.value = stock?.let {
                StockDetailUiState.Success(it)
            } ?: StockDetailUiState.Error(ErrorEntity.NotFound)
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

sealed class StockListUiState {
    data object Loading : StockListUiState()
    data class Success(val stocks: List<EodEntry>) : StockListUiState()
    data class Error(val error: ErrorEntity) : StockListUiState()
}

// UI state for stock details
sealed class StockDetailUiState {
    data object Loading : StockDetailUiState()
    data class Success(val stock: EodEntry) : StockDetailUiState()
    data class Error(val error: ErrorEntity) : StockDetailUiState()
}