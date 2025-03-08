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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EodListScreen(
    viewModel: EodDataViewModel,
    onItemClick: (String) -> Unit
) {
    val uiState by viewModel.stockListState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        // Attempt to load tickers from the external file (assets/tickers.txt)
        val tickers: String = viewModel.readStockSymbolsFromFile(context)
        viewModel.fetchStocks(tickers)
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
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.error != null -> {
                    ErrorView(
                        message = uiState.error!!,
                        onRetry = { viewModel.readStockSymbolsFromFile(context) },
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.stocks.isNotEmpty() -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(
                            items = uiState.stocks,
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