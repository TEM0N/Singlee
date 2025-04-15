package an.imation.singlee.presentation.event.posts

sealed interface PostsIntent {
    data object LoadPosts : PostsIntent
    data class SearchPosts(val query: String) : PostsIntent
}