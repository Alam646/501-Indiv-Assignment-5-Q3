
package com.example.indivassignment5q3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// Data model for a single location
data class Location(val id: Int, val name: String, val description: String)

// A static object to hold all our sample tour data
object BostonData {
    private val museums = listOf(
        Location(101, "Museum of Fine Arts", "One of the most comprehensive art museums in the world"),
        Location(102, "Museum of Science", "Interactive science exhibits and planetarium"),
        Location(103, "Isabella Stewart Gardner Museum", "Art museum in a Venetian-style palace")
    )

    private val parks = listOf(
        Location(201, "Boston Common", "America's oldest public park, established in 1634"),
        Location(202, "Public Garden", "Beautiful botanical garden with Swan Boats"),
        Location(203, "Arnold Arboretum", "A 287-acre research and public park")
    )

    private val restaurants = listOf(
        Location(301, "Union Oyster House", "Oldest continuously operating restaurant in the US"),
        Location(302, "Legal Sea Foods", "Famous for New England clam chowder"),
        Location(303, "Mike's Pastry", "Iconic North End bakery known for its cannoli")
    )

    fun getLocationsForCategory(category: String): List<Location> {
        return when (category) {
            "Museums" -> museums
            "Parks" -> parks
            "Restaurants" -> restaurants
            else -> emptyList()
        }
    }

    fun getLocationById(category: String, id: Int): Location? {
        return getLocationsForCategory(category).find { it.id == id }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to Boston!", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(8.dp))
        Text("Explore the best attractions in the city", style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = { navController.navigate(Screen.Categories.route) },
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text("Start Exploring")
        }
    }
}

@Composable
fun CategoriesScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Choose a Category", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate(Screen.LocationsList.createRoute("Museums")) },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Museums")
        }
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate(Screen.LocationsList.createRoute("Parks")) },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Parks")
        }
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate(Screen.LocationsList.createRoute("Restaurants")) },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Restaurants")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsListScreen(navController: NavController, category: String) {
    val locations = BostonData.getLocationsForCategory(category)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("All $category", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 16.dp))

        // Use a LazyColumn to efficiently display a scrollable list of items
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(locations) { location ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    onClick = {
                        navController.navigate(
                            Screen.LocationDetail.createRoute(category, location.id)
                        )
                    }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(location.name, style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(4.dp))
                        Text(location.description, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

@Composable
fun LocationDetailScreen(
    navController: NavController, 
    category: String,
    locationId: Int
) {
    val location = BostonData.getLocationById(category, locationId)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (location != null) {
            Text(location.name, style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(8.dp))
            Text("Category: $category", style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(16.dp))
            Text(location.description, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(Modifier.height(32.dp))

            // This button clears the entire back stack and returns to the Home screen.
            Button(
                onClick = {
                    navController.navigate(Screen.Home.route) {
                        // This is the key action. It pops all screens up to the Home destination.
                        popUpTo(Screen.Home.route) {
                            inclusive = true // This also removes the Home screen itself, creating a fresh start.
                        }
                        // Ensures that if the Home screen is already at the top, a new one isn't created.
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text("Return to Home")
            }
        } else {
            Text("Location not found")
        }
    }
}
