package com.uvarov.testapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.uvarov.testapp.ui.cats.CatsRoute
import com.uvarov.testapp.ui.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeDestination : NavKey

@Serializable
data object CatsDestination : NavKey

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(HomeDestination)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is HomeDestination -> NavEntry(key) {
                    HomeScreen(
                        onOpenCats = { backStack.add(CatsDestination) },
                        modifier = modifier
                    )
                }

                is CatsDestination -> NavEntry(key) {
                    CatsRoute(modifier = modifier)
                }

                else -> error("Unknown route: $key")
            }
        }
    )
}
