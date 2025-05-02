package an.imation.singlee.presentation.viewmodel

import an.imation.singlee.domain.error.TResult
import an.imation.singlee.domain.model.PostDomainModel
import an.imation.singlee.domain.usecase.AddToFavoritesUseCase
import an.imation.singlee.domain.usecase.FetchCommentsUseCase
import an.imation.singlee.domain.usecase.IsFavoriteUseCase
import an.imation.singlee.domain.usecase.RemoveFromFavoritesUseCase
import an.imation.singlee.presentation.error.parseToString
import an.imation.singlee.presentation.event.comments.PostDetailsEvent
import an.imation.singlee.presentation.event.comments.PostDetailsIntent
import an.imation.singlee.presentation.event.comments.PostDetailsState
import an.imation.singlee.presentation.ui.SingleFlowEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostDetailsViewModel(
    post: PostDomainModel,
    private val fetchCommentsUseCase: FetchCommentsUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(PostDetailsState(post = post))
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<PostDetailsEvent>(viewModelScope)
    val event = _event.flow

    fun sendIntent(intent: PostDetailsIntent) {
        when (intent) {
            PostDetailsIntent.LoadComments -> loadComments()
            PostDetailsIntent.ToggleFavorite -> toggleFavorite()
        }
    }

    init {
        viewModelScope.launch {
            val isFavorite = isFavoriteUseCase(post.id)
            _state.update { it.copy(isFavorite = isFavorite) }
        }
    }

    private fun toggleFavorite() {
        viewModelScope.launch {
            val current = _state.value.isFavorite
            if (current) {
                removeFromFavoritesUseCase(_state.value.post.id)
            } else {
                addToFavoritesUseCase(_state.value.post.id)
            }
            _state.update { it.copy(isFavorite = !current) }
        }
    }

    private fun loadComments() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            when (val result = fetchCommentsUseCase(_state.value.post.id)) {
                is TResult.Success -> {
                    _state.update {
                        it.copy(
                            comments = result.data,
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
                    _event.emit(PostDetailsEvent.ShowError(result.exception.parseToString()))
                }
            }
        }
    }
}
