package com.hzdawoud.fetchlt.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.hzdawoud.fetchlt.presentation.viewmodel.EodDataViewModel
import com.hzdawoud.fetchlt.presentation.viewmodel.StockListUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EodListScreen(
    viewModel: EodDataViewModel,
    onItemClick: (String) -> Unit
) {
    val uiState by viewModel.stockListState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        // Avoid fetching data if it already exists unless explicitly requested by the user
        if (uiState !is StockListUiState.Success) {
            val tickers: String = viewModel.readStockSymbolsFromFile(context) // Load tickers from assets/tickers.txt
            viewModel.fetchStocks(tickers)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Stocks EOD") }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when(uiState) {
                is StockListUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is StockListUiState.Error -> {
                    ErrorView(
                        modifier = Modifier.align(Alignment.Center),
                        onRetry = { viewModel.readStockSymbolsFromFile(context) }
                    )
                }

                is StockListUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(
                            items = (uiState as StockListUiState.Success).stocks,
                            key = { it.id } // Use unique key to avoid unnecessary recomposition
                        ) { stock ->
                            EodListItem(
                                entry = stock,
                                onClick = { onItemClick(stock.id) }
                            )
                        }
                    }
                }

                else -> {
                    Text(
                        text = "No stocks available",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}