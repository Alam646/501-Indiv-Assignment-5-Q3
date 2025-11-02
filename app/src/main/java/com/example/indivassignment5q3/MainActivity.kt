package com.example.indivassignment5q3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.indivassignment5q3.ui.theme.IndivAssignment5Q3Theme

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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IndivAssignment5Q3Theme {
                BostonApp()
            }
        }
    }
}


@Composable
fun BostonApp() {
    val navController = rememberNavController()
    // Add Scaffold to provide structure for the TopAppBar
    Scaffold {
        innerPadding ->
        // Move NavHost into its own NavGraph composable
        NavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}


@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Categories.route) {
            CategoriesScreen(navController = navController)
        }
        composable(
            route = Screen.LocationsList.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            LocationsListScreen(navController = navController, category = category)
        }
        composable(
            route = Screen.LocationDetail.route,
            arguments = listOf(
                navArgument("category") { type = NavType.StringType },
                navArgument("locationId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            val locationId = backStackEntry.arguments?.getInt("locationId") ?: 0
            LocationDetailScreen(category = category, locationId = locationId)
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to Boston!")
        Spacer(Modifier.height(16.dp))
        Button(onClick = { navController.navigate(Screen.Categories.route) }) {
            Text("Explore Categories")
        }
    }
}

@Composable
fun CategoriesScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Categories")
        Spacer(Modifier.height(16.dp))
        Button(onClick = { navController.navigate(Screen.LocationsList.createRoute("Museums")) }) {
            Text("Museums")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { navController.navigate(Screen.LocationsList.createRoute("Parks")) }) {
            Text("Parks")
        }
    }
}

@Composable
fun LocationsListScreen(navController: NavController, category: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Category: $category")
        Spacer(Modifier.height(16.dp))
        Button(onClick = { navController.navigate(Screen.LocationDetail.createRoute(category, 101)) }) {
            Text("View Location Details (ID: 101)")
        }
    }
}

@Composable
fun LocationDetailScreen(category: String, locationId: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Details for $category")
        Spacer(Modifier.height(8.dp))
        Text("Location ID: $locationId")
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IndivAssignment5Q3Theme {
        // Preview the whole app now
        BostonApp()
    }
}
