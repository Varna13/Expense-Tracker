package com.example.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.expensetracker.ui.screens.add.AddExpenseScreen
import com.example.expensetracker.ui.screens.home.CategoryDetailScreen
import com.example.expensetracker.ui.screens.home.HomeScreen
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            HomeScreen(
                                onAddClick = {
                                    navController.navigate("add_expense")
                                },
                                onCategoryClick = { category, month, year ->
                                    navController.navigate("category_detail/$category/$month/$year")
                                }
                            )
                        }
                        composable(
                            route = "add_expense?id={id}",
                            arguments = listOf(
                                navArgument("id") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getInt("id") ?: -1
                            AddExpenseScreen(
                                expenseId = id,
                                onDone = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(
                            route = "category_detail/{category}/{month}/{year}",
                            arguments = listOf(
                                navArgument("category") { type = NavType.StringType },
                                navArgument("month") { type = NavType.IntType },
                                navArgument("year") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            val category = backStackEntry.arguments?.getString("category") ?: ""
                            val month = backStackEntry.arguments?.getInt("month") ?: -1
                            val year = backStackEntry.arguments?.getInt("year") ?: -1
                            CategoryDetailScreen(
                                category = category,
                                month = month,
                                year = year,
                                onBackClick = {
                                    navController.popBackStack()
                                },
                                onEditClick = { expenseId ->
                                    navController.navigate("add_expense?id=$expenseId")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
