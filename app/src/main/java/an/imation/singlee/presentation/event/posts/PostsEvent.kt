package an.imation.singlee.presentation.event.posts

sealed interface PostsEvent {
    data class ShowError(val message: Int) : PostsEvent
}