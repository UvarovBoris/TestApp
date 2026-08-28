package com.uvarov.testapp.ui.catdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvarov.testapp.domain.usecase.GetCatByIdUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = CatDetailViewModelFactory::class)
class CatDetailViewModel @AssistedInject constructor(
    getCatById: GetCatByIdUseCase,
    @Assisted private val catId: String,
) : ViewModel() {

    val uiState: StateFlow<CatDetailUiState> = getCatById(catId)
        .map { cat -> CatDetailUiState(cat = cat, isLoading = false) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CatDetailUiState(isLoading = true)
        )
}

@AssistedFactory
interface CatDetailViewModelFactory {
    fun create(catId: String): CatDetailViewModel
}
