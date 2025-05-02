package an.imation.singlee.presentation.event.comments

sealed interface PostDetailsIntent {
    data object LoadComments : PostDetailsIntent
    data object ToggleFavorite : PostDetailsIntent

}