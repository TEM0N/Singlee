package an.imation.singlee.presentation.viewmodel

import an.imation.singlee.domain.getMyDataPage
import an.imation.singlee.presentation.event.pagination.PaginationEvent
import an.imation.singlee.presentation.event.pagination.PaginationIntent
import an.imation.singlee.presentation.event.pagination.PaginationState
import an.imation.singlee.presentation.ui.SingleFlowEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PaginationViewModel : ViewModel() {
    private val _state = MutableStateFlow(PaginationState())
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<PaginationEvent>(viewModelScope)
    val event = _event.flow

    fun sendIntent(intent: PaginationIntent) {
        when (intent) {
            PaginationIntent.LoadFirstPage -> loadFirstPage()
            PaginationIntent.LoadNextPage -> loadNextPage()
        }
    }

    private val _scrollState = MutableStateFlow(0)
    val scrollState: StateFlow<Int> = _scrollState.asStateFlow()

    private companion object {
        const val LOAD_AHEAD_ITEMS = 10
    }

    fun handleScroll(lastVisibleIndex: Int) {
        _scrollState.value = lastVisibleIndex
        checkShouldLoadNextPage()
    }

    private fun checkShouldLoadNextPage() {
        val currentState = _state.value
        if (shouldLoadNextPage(currentState)) {
            loadNextPage()
        }
    }

    private fun shouldLoadNextPage(state: PaginationState): Boolean {
        return !state.isLoading &&
                !state.isAllDataLoaded &&
                state.currentPage < state.maxPage &&
                _scrollState.value >= state.items.size - LOAD_AHEAD_ITEMS
    }

    private fun loadFirstPage() {
        if (_state.value.currentPage > 0) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            loadPage(1)
        }
    }

    private fun loadNextPage() {
        val currentState = _state.value
        if (currentState.isLoading ||
            currentState.isAllDataLoaded ||
            currentState.currentPage >= currentState.maxPage ||
            currentState.error != null) {
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            loadPage(currentState.currentPage + 1)
        }
    }

    private suspend fun loadPage(page: Int) {
        getMyDataPage(page)
            .onSuccess { dataPage ->
                _state.update { currentState ->
                    val newItems = if (page == 1) dataPage.data else currentState.items + dataPage.data
                    currentState.copy(
                        items = newItems,
                        currentPage = page,
                        maxPage = dataPage.maxPage,
                        isLoading = false,
                        isAllDataLoaded = page >= dataPage.maxPage,
                        error = null
                    )
                }
            }
            .onFailure { exception ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = exception.message ?: "Unknown error"
                    )
                }
                _event.emit(PaginationEvent.ShowError(exception.message ?: "Unknown error"))
            }
    }
}