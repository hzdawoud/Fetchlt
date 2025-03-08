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
import com.hzdawoud.fetchlt.utils.StringUtil.formatted

@SuppressLint("DefaultLocale")
@Composable
fun EodListItem(
    entry: EodEntry,
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
                model = getStockLogoUrl(entry.symbol),
                contentDescription = "${entry.symbol} logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = entry.symbol,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = entry.exchange,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "$${entry.close}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = "${if (entry.isPositive) "+" else ""}${entry.priceChange.formatted()} (${entry.percentChange.formatted()}%)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (entry.isPositive) green else Color.Red
                )
            }
        }
    }
}