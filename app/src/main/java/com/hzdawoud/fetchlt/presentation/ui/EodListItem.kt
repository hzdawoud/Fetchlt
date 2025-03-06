package com.hzdawoud.fetchlt.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hzdawoud.fetchlt.domain.model.EodEntry
import com.hzdawoud.fetchlt.ui.theme.green
import com.hzdawoud.fetchlt.utils.CoilUtil.getStockLogoUrl

@SuppressLint("DefaultLocale")
@Composable
fun EodListItem(
    stock: EodEntry,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Stock logo
            AsyncImage(
                model = getStockLogoUrl(stock.symbol),
                contentDescription = "${stock.symbol} logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stock.symbol,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = stock.exchange,
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "$${stock.close}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                val priceChange = stock.close - stock.open
                val percentChange = (priceChange / stock.open) * 100
                val isPositive = priceChange >= 0

                Text(
                    text = "${if (isPositive) "+" else ""}${String.format("%.2f", priceChange)} (${String.format("%.2f", percentChange)}%)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isPositive) green else Color.Red
                )
            }
        }
    }
}