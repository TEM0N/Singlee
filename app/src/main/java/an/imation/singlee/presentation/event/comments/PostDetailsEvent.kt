package an.imation.singlee.presentation.event.comments

sealed interface PostDetailsEvent {
    data class ShowError(val message: Int) : PostDetailsEvent
}