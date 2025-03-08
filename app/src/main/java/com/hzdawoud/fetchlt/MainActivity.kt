package com.hzdawoud.fetchlt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.hzdawoud.fetchlt.presentation.ui.StockNavHost
import com.hzdawoud.fetchlt.ui.theme.FetchltTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FetchltTheme {
                val navController = rememberNavController()
                StockNavHost(navController = navController)
            }
        }
    }
}