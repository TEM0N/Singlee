package an.imation.singlee.presentation.event.pagination

sealed interface PaginationEvent {
    data class ShowError(val message: String) : PaginationEvent
}