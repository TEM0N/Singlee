package an.imation.singlee.presentation.viewmodel

import an.imation.singlee.domain.error.TResult
import an.imation.singlee.domain.model.PostDomainModel
import an.imation.singlee.domain.usecase.AddToFavoritesUseCase
import an.imation.singlee.domain.usecase.FetchPostsUseCase
import an.imation.singlee.domain.usecase.GetFavoritesUseCase
import an.imation.singlee.domain.usecase.IsFavoriteUseCase
import an.imation.singlee.domain.usecase.RemoveFromFavoritesUseCase
import an.imation.singlee.presentation.error.parseToString
import an.imation.singlee.presentation.event.posts.PostsEvent
import an.imation.singlee.presentation.event.posts.PostsIntent
import an.imation.singlee.presentation.event.posts.PostsState
import an.imation.singlee.presentation.ui.SingleFlowEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostsViewModel(
    private val fetchPostsUseCase: FetchPostsUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(PostsState())
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<PostsEvent>(viewModelScope)
    val event = _event.flow

    init {
        viewModelScope.launch {
            getFavoritesUseCase().collect { favorites ->
                _state.update { currentState ->
                    currentState.copy(favoritePostIds = favorites.toSet())
                }
            }
        }

        viewModelScope.launch {
            _state
                .distinctUntilChangedBy { it.posts.toString() + it.searchQuery + it.favoritePostIds }
                .collect { triggerState ->
                    val filtered = if (triggerState.searchQuery.isNotEmpty()) {
                        triggerState.posts.filter {
                            it.title.contains(triggerState.searchQuery, true) ||
                                    it.body.contains(triggerState.searchQuery, true)
                        }
                    } else {
                        triggerState.posts
                    }

                    val sorted = filtered.sortedWith(compareByDescending<PostDomainModel> {
                        triggerState.favoritePostIds.contains(it.id)
                    }.thenBy { it.id })

                    _state.update {
                        it.copy(filteredPosts = sorted)
                    }
                }
        }
    }

    fun sendIntent(intent: PostsIntent) {
        when (intent) {
            PostsIntent.LoadPosts -> loadPosts()
            is PostsIntent.SearchPosts -> searchPosts(intent.query)
            is PostsIntent.ToggleFavorite -> toggleFavorite(intent.postId)
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

    private fun toggleFavorite(postId: Int) {
        viewModelScope.launch {
            val isFavorite = isFavoriteUseCase(postId)
            if (isFavorite) {
                removeFromFavoritesUseCase(postId)
            } else {
                addToFavoritesUseCase(postId)
            }
        }
    }
}