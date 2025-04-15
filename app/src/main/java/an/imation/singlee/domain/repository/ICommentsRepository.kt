package an.imation.singlee.domain.repository

import an.imation.singlee.domain.error.PostExceptionDomainModel
import an.imation.singlee.domain.error.TResult
import an.imation.singlee.domain.model.CommentDomainModel

interface ICommentsRepository {
    suspend fun fetchComments(postId: Int): TResult<List<CommentDomainModel>, PostExceptionDomainModel>
}