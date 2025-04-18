package an.imation.singlee.presentation.viewmodel

import an.imation.singlee.domain.error.PostExceptionDomainModel
import an.imation.singlee.domain.error.TResult
import an.imation.singlee.domain.usecase.FetchPostsUseCase
import an.imation.singlee.presentation.error.parseToString
import an.imation.singlee.presentation.event.posts.PostsEvent
import an.imation.singlee.presentation.event.posts.PostsIntent
import an.imation.singlee.presentation.event.posts.PostsState
import an.imation.singlee.presentation.ui.SingleFlowEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostsViewModel(
    private val fetchPostsUseCase: FetchPostsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(PostsState())
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<PostsEvent>(viewModelScope)
    val event = _event.flow

    fun sendIntent(intent: PostsIntent) {
        when (intent) {
            PostsIntent.LoadPosts -> loadPosts()
            is PostsIntent.SearchPosts -> searchPosts(intent.query)
        }
    }

    init {
        viewModelScope.launch {
            _state
                .distinctUntilChangedBy {
                    it.filteredPosts.toString() + it.searchQuery
                }
                .collect { triggerState ->
                    val filter = triggerState.posts.filter{
                        it.title.contains(triggerState.searchQuery, false) ||
                                it.body.contains(triggerState.searchQuery, false)
                    }
                    _state.update { it.copy(filteredPosts = filter) }
                }
        }

    }

    private fun searchPosts(query: String) {
        _state.update { currentState ->
            currentState.copy(searchQuery = query)
        }
    }

    private fun loadPosts() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            when (val result = fetchPostsUseCase()) {
                is TResult.Success -> {
                    _state.update {
                        it.copy(
                            posts = result.data,
                            filteredPosts = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                }

                is TResult.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.exception.parseToString()
                        )
                    }
                    _event.emit(PostsEvent.ShowError(result.exception.parseToString()))
                }
            }
        }
    }
}