package com.uvarov.testapp.ui.catdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvarov.testapp.domain.repository.FavoritesRepository
import com.uvarov.testapp.domain.usecase.GetCatByIdUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CatDetailViewModelFactory::class)
class CatDetailViewModel @AssistedInject constructor(
    getCatById: GetCatByIdUseCase,
    private val favoritesRepository: FavoritesRepository,
    @Assisted private val catId: String,
) : ViewModel() {

    val uiState: StateFlow<CatDetailUiState> = getCatById(catId)
        .map { cat -> CatDetailUiState(cat = cat, isLoading = false) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CatDetailUiState(isLoading = true)
        )

    val isFavorite: StateFlow<Boolean> = favoritesRepository.isFavorite(catId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun toggleFavorite() {
        viewModelScope.launch {
            val current = favoritesRepository.isFavorite(catId).first()
            if (current) {
                favoritesRepository.removeFavorite(catId)
            } else {
                favoritesRepository.addFavorite(catId)
            }
        }
    }
}

@AssistedFactory
interface CatDetailViewModelFactory {
    fun create(catId: String): CatDetailViewModel
}
