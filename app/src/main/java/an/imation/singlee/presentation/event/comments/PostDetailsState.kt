package an.imation.singlee.presentation.event.comments

import an.imation.singlee.domain.model.CommentDomainModel
import an.imation.singlee.domain.model.PostDomainModel

data class PostDetailsState(
    val post: PostDomainModel,
    val comments: List<CommentDomainModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)