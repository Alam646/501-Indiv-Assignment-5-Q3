package com.example.indivassignment5q3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.indivassignment5q3.ui.theme.IndivAssignment5Q3Theme

// 1. Update sealed class with a route that takes an argument
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Categories : Screen("categories")
    object LocationsList : Screen("locations/{category}") {
        // Helper function to create the route with the actual category
        fun createRoute(category: String) = "locations/$category"
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IndivAssignment5Q3Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.Home.route) {
                        composable(Screen.Home.route) {
                            HomeScreen(navController = navController)
                        }
                        composable(Screen.Categories.route) {
                            CategoriesScreen(navController = navController)
                        }
                        // 2. Add the new destination to the NavHost
                        composable(
                            route = Screen.LocationsList.route,
                            arguments = listOf(navArgument("category") { type = NavType.StringType })
                        ) { backStackEntry ->
                            // Retrieve the argument from the back stack
                            val category = backStackEntry.arguments?.getString("category") ?: ""
                            LocationsListScreen(navController = navController, category = category)
                        }
                    }
                }
            }
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
        // 3. Update buttons to pass the category argument
        Button(onClick = { navController.navigate(Screen.LocationsList.createRoute("Museums")) }) {
            Text("Museums")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { navController.navigate(Screen.LocationsList.createRoute("Parks")) }) {
            Text("Parks")
        }
    }
}

// 4. Create the new LocationsListScreen composable
@Composable
fun LocationsListScreen(navController: NavController, category: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Category: $category")
        Spacer(Modifier.height(16.dp))
        Button(onClick = { /* Navigation to detail screen will be added later */ }) {
            Text("View Location Details (ID: 101)")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IndivAssignment5Q3Theme {
        HomeScreen(navController = rememberNavController())
    }
}
