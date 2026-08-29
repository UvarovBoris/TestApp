package com.uvarov.testapp.ui.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uvarov.testapp.core.ui.component.CatGridItem
import com.uvarov.testapp.core.ui.theme.TestAppTheme
import com.uvarov.testapp.domain.model.Breed
import com.uvarov.testapp.domain.model.Cat

@Composable
fun FavoritesRoute(
    onCatClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    FavoritesScreen(
        state = uiState,
        onCatClick = onCatClick,
        onRemoveFavorite = viewModel::removeFavorite,
        modifier = modifier
    )
}

@Composable
fun FavoritesScreen(
    state: FavoritesUiState,
    onCatClick: (String) -> Unit,
    onRemoveFavorite: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.cats.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No favorites yet",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(state.cats, key = { it.id }) { cat ->
                Box {
                    CatGridItem(
                        cat = cat,
                        onClick = { onCatClick(cat.id) }
                    )
                    IconButton(
                        onClick = { onRemoveFavorite(cat.id) },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Remove favorite",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    TestAppTheme {
        FavoritesScreen(
            state = FavoritesUiState(
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
            onCatClick = {},
            onRemoveFavorite = {}
        )
    }
}
