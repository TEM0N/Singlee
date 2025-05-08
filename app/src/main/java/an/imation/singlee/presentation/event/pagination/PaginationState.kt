package an.imation.singlee.presentation.event.pagination

data class PaginationState(
    val items: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 0,
    val maxPage: Int = 0,
    val isAllDataLoaded: Boolean = false
)