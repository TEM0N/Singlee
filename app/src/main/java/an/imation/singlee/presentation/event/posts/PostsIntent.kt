package an.imation.singlee.presentation.event.posts

sealed interface PostsIntent {
    data object LoadPosts : PostsIntent
}