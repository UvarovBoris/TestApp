package com.uvarov.testapp.ui.cats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvarov.testapp.domain.usecase.GetCatsUseCase
import com.uvarov.testapp.domain.usecase.RefreshCatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class CatsViewModel @Inject constructor(
    getCats: GetCatsUseCase,
    private val refreshCatsUseCase: RefreshCatsUseCase
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)

    val uiState: StateFlow<CatsUiState> = combine(
        getCats(),
        _isRefreshing
    ) { cats, isRefreshing ->
        CatsUiState(
            cats = cats,
            isLoading = cats.isEmpty() && !isRefreshing,
            isRefreshing = isRefreshing
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CatsUiState(isLoading = true)
    )

    fun refreshCats() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                refreshCatsUseCase()
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}
