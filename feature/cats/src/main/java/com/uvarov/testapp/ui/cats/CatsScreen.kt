package com.uvarov.testapp.ui.cats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uvarov.testapp.core.ui.component.CatGridItem
import com.uvarov.testapp.core.ui.theme.TestAppTheme
import com.uvarov.testapp.domain.model.Breed
import com.uvarov.testapp.domain.model.Cat

@Composable
fun CatsRoute(
    onCatClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CatsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CatsScreen(
        state = uiState,
        onRefresh = viewModel::refreshCats,
        onLoadNextPage = viewModel::loadNextPage,
        onCatClick = onCatClick,
        modifier = modifier
    )
}

@Composable
fun CatsScreen(
    state: CatsUiState,
    onRefresh: () -> Unit,
    onLoadNextPage: () -> Unit,
    onCatClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val gridState = rememberLazyGridState()

    val shouldLoadMore by remember {
        derivedStateOf {
            val totalItems = gridState.layoutInfo.totalItemsCount
            val lastVisibleItemIndex = gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            totalItems == 0 || lastVisibleItemIndex >= totalItems - 6
        }
    }

    val itemsCount by remember {
        derivedStateOf {
            gridState.layoutInfo.totalItemsCount
        }
    }

    LaunchedEffect(state.isLoading, shouldLoadMore, itemsCount) {
        if (!state.isLoading && shouldLoadMore && state.canLoadMore && !state.isLoadingMore && !state.isRefreshing) {
            onLoadNextPage()
        }
    }

    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = onRefresh,
            modifier = Modifier.fillMaxSize()
        ) {
            val layoutDirection = LocalLayoutDirection.current
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                state = gridState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = innerPadding.calculateStartPadding(layoutDirection) + 4.dp,
                    top = innerPadding.calculateTopPadding() + 4.dp,
                    end = innerPadding.calculateEndPadding(layoutDirection) + 4.dp,
                    bottom = innerPadding.calculateBottomPadding() + 4.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(state.cats, key = { it.id }) { cat ->
                    CatGridItem(
                        cat = cat,
                        onClick = { onCatClick(cat.id) }
                    )
                }
                if (state.isLoadingMore) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatsScreenPreview() {
    TestAppTheme {
        CatsScreen(
            state = CatsUiState(
                cats = listOf(
                    Cat(
                        id = "aph",
                        imageUrl = "https://cdn2.thecatapi.com/images/aph.jpg",
                        breeds = listOf(
                            Breed(
                                id = "abys",
                                name = "Abyssinian",
                                temperament = null,
                                origin = null,
                                lifeSpan = null,
                                description = null
                            )
                        )
                    ),
                    Cat(id = "bmp", imageUrl = "https://cdn2.thecatapi.com/images/bmp.jpg")
                )
            ),
            onRefresh = {},
            onLoadNextPage = {},
            onCatClick = {}
        )
    }
}