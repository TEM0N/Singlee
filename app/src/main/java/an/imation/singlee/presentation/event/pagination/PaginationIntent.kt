package an.imation.singlee.presentation.event.pagination

sealed interface PaginationIntent {
    data object LoadFirstPage : PaginationIntent
    data object LoadNextPage : PaginationIntent
}