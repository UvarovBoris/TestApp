package com.uvarov.testapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.uvarov.testapp.ui.catdetail.CatDetailRoute
import com.uvarov.testapp.ui.catdetail.CatDetailViewModel
import com.uvarov.testapp.ui.catdetail.CatDetailViewModelFactory
import com.uvarov.testapp.ui.cats.CatsRoute
import com.uvarov.testapp.ui.main.MainRoute
import kotlinx.serialization.Serializable

@Serializable
data object MainDestination : NavKey

@Serializable
data object CatsDestination : NavKey

@Serializable
data class CatDetailDestination(val catId: String) : NavKey

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(MainDestination)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = { key ->
            when (key) {
                is MainDestination -> NavEntry(key) {
                    MainRoute(
                        onOpenCats = { backStack.add(CatsDestination) },
                        modifier = modifier
                    )
                }

                is CatsDestination -> NavEntry(key) {
                    CatsRoute(
                        onCatClick = { catId -> backStack.add(CatDetailDestination(catId)) },
                        modifier = modifier
                    )
                }

                is CatDetailDestination -> NavEntry(key) {
                    val viewModel: CatDetailViewModel = hiltViewModel<CatDetailViewModel, CatDetailViewModelFactory>(
                        creationCallback = { factory -> factory.create(key.catId) }
                    )
                    CatDetailRoute(
                        onBack = { backStack.removeLastOrNull() },
                        modifier = modifier,
                        viewModel = viewModel
                    )
                }

                else -> error("Unknown route: $key")
            }
        }
    )
}
