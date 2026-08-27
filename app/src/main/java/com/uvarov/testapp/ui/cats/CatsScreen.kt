package com.uvarov.testapp.ui.cats

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.uvarov.testapp.data.model.Breed
import com.uvarov.testapp.data.model.Cat
import com.uvarov.testapp.ui.theme.TestAppTheme

@Composable
fun CatsRoute(
    onCatClick: (Cat) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CatsViewModel = hiltViewModel()
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
    onCatClick: (Cat) -> Unit,
    modifier: Modifier = Modifier
) {
    val gridState = rememberLazyGridState()

    val shouldLoadMore by remember {
        derivedStateOf {
            val totalItems = gridState.layoutInfo.totalItemsCount
            val lastVisibleItemIndex = gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            totalItems > 0 && lastVisibleItemIndex >= totalItems - 6
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore && state.canLoadMore && !state.isLoadingMore && !state.isRefreshing && !state.isLoading) {
            onLoadNextPage()
        }
    }

    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = onRefresh,
            modifier = Modifier.fillMaxSize()
        ) {
            when {
                state.isLoading -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }

                state.cats.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No cats yet",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                else -> {
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
                            CatImage(
                                cat = cat,
                                onClick = { onCatClick(cat) }
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
    }
}

@Composable
private fun CatImage(
    cat: Cat,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .aspectRatio(1f)
        .clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = cat.imageUrl,
            contentDescription = cat.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        val breedNames = cat.breeds.joinToString(", ") { it.name }
        if (breedNames.isNotEmpty()) {
            Text(
                text = breedNames,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
                    .padding(4.dp),
                style = MaterialTheme.typography.labelSmall
            )
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
                        name = "Whiskers",
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
                    Cat(id = "bmp", name = "Milo", imageUrl = "https://cdn2.thecatapi.com/images/bmp.jpg")
                )
            ),
            onRefresh = {},
            onLoadNextPage = {},
            onCatClick = {}
        )
    }
}
