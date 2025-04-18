package an.imation.singlee.presentation.event.posts

import an.imation.singlee.domain.model.PostDomainModel

data class PostsState(
    val posts: List<PostDomainModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: Int? = null,
    val searchQuery: String = "",
    val filteredPosts: List<PostDomainModel> = emptyList()
)