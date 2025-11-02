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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.indivassignment5q3.ui.theme.IndivAssignment5Q3Theme

// 1. Define routes for the screens in a sealed class for type safety
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Categories : Screen("categories")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IndivAssignment5Q3Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    // 2. Set up the NavController and NavHost
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.Home.route) {
                        composable(Screen.Home.route) {
                            HomeScreen(navController = navController)
                        }
                        composable(Screen.Categories.route) {
                            CategoriesScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) { // Pass NavController
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to Boston!")
        Spacer(Modifier.height(16.dp))
        // 3. Add the navigation action to the button
        Button(onClick = { navController.navigate(Screen.Categories.route) }) {
            Text("Explore Categories")
        }
    }
}

@Composable
fun CategoriesScreen(navController: NavController) { // Pass NavController
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Categories")
        Spacer(Modifier.height(16.dp))
        Button(onClick = { /* Navigation will be added later */ }) {
            Text("Museums")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { /* Navigation will be added later */ }) {
            Text("Parks")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IndivAssignment5Q3Theme {
        // Preview won't have real navigation, so we pass a dummy NavController
        // A better approach for previews is to create a fake NavController, but this works for now.
        HomeScreen(navController = rememberNavController())
    }
}