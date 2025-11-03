package com.example.indivassignment5q3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.indivassignment5q3.ui.theme.IndivAssignment5Q3Theme

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BostonTopAppBar(
    currentScreenTitle: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(currentScreenTitle) },
        modifier = modifier,
        navigationIcon = {
            // The navigation icon (back arrow) is only shown if canNavigateBack is true
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        // This makes the TopAppBar use the primary color from our theme for a branded look.
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
fun BostonApp() {
    val navController = rememberNavController()
    // By observing the back stack entry, we can react to navigation changes.
    val backStackEntry by navController.currentBackStackEntryAsState()

    // Determine the current screen's title. This will update whenever the route changes.
    val currentScreenTitle = when (backStackEntry?.destination?.route) {
        Screen.Home.route -> "Explore Boston"
        Screen.Categories.route -> "Categories"
        Screen.LocationsList.route -> {
            val category = backStackEntry?.arguments?.getString("category")
            "All ${category ?: "Locations"}"
        }
        Screen.LocationDetail.route -> "Location Details"
        else -> "Boston Tour"
    }

    // This logic determines if we are at the Home screen because the app just started
    // or because the user completed a full loop and cleared the stack.
    val isHomeAfterFullCycle = backStackEntry?.destination?.route == Screen.Home.route &&
            navController.previousBackStackEntry == null

    Scaffold(
        topBar = {
            BostonTopAppBar(
                currentScreenTitle = currentScreenTitle,
                // The back button is disabled if there's no screen to go back to.
                // This is true at the start of the app and after returning home from the detail screen.
                canNavigateBack = !isHomeAfterFullCycle,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        BostonNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
