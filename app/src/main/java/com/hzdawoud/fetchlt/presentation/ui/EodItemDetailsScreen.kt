package com.hzdawoud.fetchlt.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hzdawoud.fetchlt.presentation.viewmodel.EodDataViewModel
import com.hzdawoud.fetchlt.ui.theme.green
import com.hzdawoud.fetchlt.utils.CoilUtil.getStockLogoUrl
import com.hzdawoud.fetchlt.utils.DateUtil

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EodDetailScreen(
    id: String,
    onBackClick: () -> Unit,
    viewModel: EodDataViewModel
) {
    val uiState by viewModel.stockDetailState.collectAsState()

    LaunchedEffect(id) {
        viewModel.loadStockDetails(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Stock EOD Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
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
                        onRetry = { viewModel.loadStockDetails(id) },
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                uiState.stock != null -> {
                    val stock = uiState.stock!!

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        // Header with logo and basic info
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = getStockLogoUrl(stock.symbol),
                                contentDescription = "${stock.symbol} logo",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.surface)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {
                                Text(
                                    text = stock.symbol,
                                    style = MaterialTheme.typography.headlineLarge
                                )
                                Text(
                                    text = "Exchange: ${stock.exchange}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = "Date: ${DateUtil.formattedDate(stock.date)}",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Price information card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 4.dp
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Price Information",
                                    style = MaterialTheme.typography.headlineSmall
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        DetailScreenCell("Open", "$${stock.open}")
                                        DetailScreenCell("Close", "$${stock.close}")
                                        DetailScreenCell("High", "$${stock.high}")
                                        DetailScreenCell("Low", "$${stock.low}")
                                        DetailScreenCell("Volume", "${stock.volume.toInt()}")

                                        val priceChange = stock.close - stock.open
                                        val percentChange = (priceChange / stock.open) * 100
                                        val isPositive = priceChange >= 0

                                        DetailScreenCell(
                                            "Change",
                                            "${if (isPositive) "+" else ""}${String.format("%.2f", priceChange)} (${String.format("%.2f", percentChange)}%)",
                                            if (isPositive) green else Color.Red
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Adjusted values card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 4.dp
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Adjusted Values",
                                    style = MaterialTheme.typography.headlineSmall
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        DetailScreenCell("Adj Open", "$${stock.adjOpen}")
                                        DetailScreenCell("Adj Close", "$${stock.adjClose}")
                                        DetailScreenCell("Adj High", "$${stock.adjHigh}")

                                        DetailScreenCell("Adj Low", "$${stock.adjLow}")
                                        DetailScreenCell("Adj Volume", "${stock.adjVolume.toInt()}")
                                        DetailScreenCell("Split Factor", "${stock.splitFactor}")
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Dividend info
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 4.dp
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Dividend Information",
                                    style = MaterialTheme.typography.headlineSmall
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                DetailScreenCell("Dividend", "$${stock.dividend}")

                                if (stock.dividend > 0) {
                                    val dividendYield = (stock.dividend / stock.close) * 100
                                    DetailScreenCell("Dividend Yield", "${String.format("%.2f", dividendYield)}%")
                                } else {
                                    DetailScreenCell("Dividend Yield", "No dividend")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}