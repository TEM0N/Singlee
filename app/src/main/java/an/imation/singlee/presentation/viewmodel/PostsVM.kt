package an.imation.singlee.presentation.viewmodel

import an.imation.singlee.domain.error.PostExceptionDomainModel
import an.imation.singlee.domain.error.TResult
import an.imation.singlee.domain.usecase.FetchPostsUseCase
import an.imation.singlee.presentation.event.posts.PostsEvent
import an.imation.singlee.presentation.event.posts.PostsIntent
import an.imation.singlee.presentation.event.posts.PostsState
import an.imation.singlee.presentation.ui.SingleFlowEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostsVM(
    private val fetchPostsUseCase: FetchPostsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(PostsState())
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<PostsEvent>(viewModelScope)
    val event = _event.flow

    fun sendIntent(intent: PostsIntent) {
        when (intent) {
            PostsIntent.LoadPosts -> loadPosts()
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
                            error = when (result.exception) {
                                is PostExceptionDomainModel.NoInternetConnection -> "Ошибка интернет соединения"
                                is PostExceptionDomainModel.Other ->
                                    result.exception.cause?.message ?: "Ошибка загрузки"
                            }
                        )
                    }
                }
            }
        }
    }

}