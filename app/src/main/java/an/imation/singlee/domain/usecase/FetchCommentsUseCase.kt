package an.imation.singlee.domain.usecase

import an.imation.singlee.domain.error.PostExceptionDomainModel
import an.imation.singlee.domain.error.TResult
import an.imation.singlee.domain.model.CommentDomainModel
import an.imation.singlee.domain.repository.ICommentsRepository

class FetchCommentsUseCase(
    private val repository: ICommentsRepository
) {
    suspend operator fun invoke(postId: Int): TResult<List<CommentDomainModel>, PostExceptionDomainModel> {
        return repository.fetchComments(postId)
    }
}