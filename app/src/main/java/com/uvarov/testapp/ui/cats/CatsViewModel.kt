package com.uvarov.testapp.ui.cats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvarov.testapp.domain.usecase.GetCatsUseCase
import com.uvarov.testapp.domain.usecase.LoadNextPageCatsUseCase
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
    private val refreshCatsUseCase: RefreshCatsUseCase,
    private val loadNextPageCatsUseCase: LoadNextPageCatsUseCase
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    private val _isLoadingMore = MutableStateFlow(false)
    private val _canLoadMore = MutableStateFlow(true)

    val uiState: StateFlow<CatsUiState> = combine(
        getCats(),
        _isRefreshing,
        _isLoadingMore,
        _canLoadMore
    ) { cats, isRefreshing, isLoadingMore, canLoadMore ->
        CatsUiState(
            cats = cats,
            isLoading = cats.isEmpty() && !isRefreshing,
            isRefreshing = isRefreshing,
            isLoadingMore = isLoadingMore,
            canLoadMore = canLoadMore
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
                _canLoadMore.value = true
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun loadNextPage() {
        if (_isLoadingMore.value || !_canLoadMore.value || uiState.value.isLoading || _isRefreshing.value) {
            return
        }

        viewModelScope.launch {
            _isLoadingMore.value = true
            try {
                val hasMore = loadNextPageCatsUseCase()
                _canLoadMore.value = hasMore
            } finally {
                _isLoadingMore.value = false
            }
        }
    }
}
