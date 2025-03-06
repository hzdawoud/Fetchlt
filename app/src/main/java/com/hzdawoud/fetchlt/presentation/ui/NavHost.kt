package com.hzdawoud.fetchlt.presentation.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hzdawoud.fetchlt.presentation.viewmodel.EodDataViewModel
import com.hzdawoud.fetchlt.utils.navigation.Screen

@Composable
fun StockNavHost(
    navController: NavHostController,
    viewModel: EodDataViewModel = hiltViewModel()  // Hilt auto-provides it
) {
    NavHost(
        navController = navController,
        startDestination = Screen.StockList.route
    ) {
        composable(Screen.StockList.route) {
            EodListScreen(
                onItemClick = { symbol ->
                    navController.navigate(Screen.StockDetail.createRoute(symbol))
                },
                viewModel = viewModel
            )
        }
        composable(
            route = Screen.StockDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val symbol = backStackEntry.arguments?.getString("id") ?: ""
            EodDataDetailScreen(
                id = symbol,
                onBackClick = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
    }
}