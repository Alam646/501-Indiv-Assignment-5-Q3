
package com.example.indivassignment5q3

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

// Defines the routes for the different screens in a type-safe way
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Categories : Screen("categories")
    object LocationsList : Screen("locations/{category}") {
        fun createRoute(category: String) = "locations/$category"
    }
    object LocationDetail : Screen("locations/{category}/{locationId}") {
        fun createRoute(category: String, locationId: Int) = "locations/$category/$locationId"
    }
}

@Composable
fun BostonNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        // Route for the Home screen
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        
        // Route for the Categories screen
        composable(Screen.Categories.route) {
            CategoriesScreen(navController = navController)
        }
        
        // Route for the list of locations, which requires a "category" argument
        composable(
            route = Screen.LocationsList.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            LocationsListScreen(navController = navController, category = category)
        }
        
        // Route for the location detail screen, requiring both a "category" and a "locationId"
        composable(
            route = Screen.LocationDetail.route,
            arguments = listOf(
                navArgument("category") { type = NavType.StringType },
                navArgument("locationId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            val locationId = backStackEntry.arguments?.getInt("locationId") ?: 0
            LocationDetailScreen(
                navController = navController,
                category = category,
                locationId = locationId
            )
        }
    }
}
