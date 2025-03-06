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
import androidx.compose.ui.unit.dp
import com.hzdawoud.fetchlt.presentation.viewmodel.EodDataViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EodListScreen(
    onItemClick: (String) -> Unit,
    viewModel: EodDataViewModel
) {
    val uiState by viewModel.stockListState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchStocks("AAPL,MSFT,GOOG,AMZN,META,TSLA")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Stock End of Day Data") }
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
                        onRetry = { viewModel.fetchStocks("AAPL,MSFT,GOOG,AMZN,META,TSLA") },
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
                                stock = stock,
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