package com.uvarov.testapp.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uvarov.testapp.ui.favorites.FavoritesRoute
import com.uvarov.testapp.ui.home.HomeRoute
import com.uvarov.testapp.ui.profile.ProfileRoute
import com.uvarov.testapp.ui.theme.TestAppTheme

@Composable
fun MainRoute(
    onOpenCats: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    MainScreen(
        state = uiState,
        onTabSelected = viewModel::onTabSelected,
        onOpenCats = onOpenCats,
        modifier = modifier
    )
}

@Composable
fun MainScreen(
    state: MainUiState,
    onTabSelected: (MainTab) -> Unit,
    onOpenCats: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val saveableStateHolder = rememberSaveableStateHolder()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                MainTab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = state.selectedTab == tab,
                        onClick = { onTabSelected(tab) },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.title
                            )
                        },
                        label = { Text(text = tab.title) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            saveableStateHolder.SaveableStateProvider(key = state.selectedTab) {
                when (state.selectedTab) {
                    MainTab.HOME -> HomeRoute(onOpenCats = onOpenCats)
                    MainTab.FAVORITES -> FavoritesRoute()
                    MainTab.PROFILE -> ProfileRoute()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    TestAppTheme {
        MainScreen(
            state = MainUiState(selectedTab = MainTab.HOME),
            onTabSelected = {},
            onOpenCats = {}
        )
    }
}
