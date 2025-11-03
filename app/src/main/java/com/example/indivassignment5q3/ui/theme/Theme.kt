package com.example.indivassignment5q3.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// A dark color scheme inspired by Boston's maritime history and night sky.
private val DarkColorScheme = darkColorScheme(
    primary = BrickRed,       // The main interactive color for buttons and accents
    secondary = DarkBrickRed,   // A darker shade for less prominent components
    background = DeepNavy,      // A deep navy for screen backgrounds
    surface = MidNavy,          // A slightly lighter navy for surfaces like cards
    onPrimary = Cream,          // Text color on primary surfaces (e.g., on a red button)
    onSecondary = Cream,        // Text color on secondary surfaces
    onBackground = Cream,       // Main text color for content on the background
    onSurface = Cream           // Text color for content on cards and other surfaces
)

// A light color scheme inspired by Boston's historic brick and cream architecture.
private val LightColorScheme = lightColorScheme(
    primary = BrickRed,       // The main interactive color
    secondary = DarkBrickRed,   // A darker accent
    background = Cream,         // A warm, off-white for screen backgrounds
    surface = Color.White,      // A clean white for elevated surfaces like cards
    onPrimary = Color.White,    // Text on primary buttons
    onSecondary = Color.White,  // Text on secondary surfaces
    onBackground = DeepNavy,    // Main text color is navy for high contrast
    onSurface = DeepNavy        // Text on cards is also navy
)

@Composable
fun IndivAssignment5Q3Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is disabled to enforce the custom Boston theme
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
