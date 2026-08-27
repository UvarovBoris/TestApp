package com.uvarov.testapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.uvarov.testapp.ui.home.HomeRoute
import kotlinx.serialization.Serializable

@Serializable
data object HomeDestination : NavKey

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(HomeDestination)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is HomeDestination -> NavEntry(key) {
                    HomeRoute(modifier = modifier)
                }

                else -> error("Unknown route: $key")
            }
        }
    )
}
