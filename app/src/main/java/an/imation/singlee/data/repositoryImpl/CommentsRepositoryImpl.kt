package an.imation.singlee.data.repositoryImpl

import an.imation.singlee.data.api.IPostApi
import an.imation.singlee.data.mapper.CommentDataMapper
import an.imation.singlee.data.mapper.toPostExceptionDomainModel
import an.imation.singlee.domain.error.PostExceptionDomainModel
import an.imation.singlee.domain.error.TResult
import an.imation.singlee.domain.model.CommentDomainModel
import an.imation.singlee.domain.repository.ICommentsRepository

class CommentsRepositoryImpl(
    private val apiService: IPostApi,
    private val mapper: CommentDataMapper
) : ICommentsRepository {
    override suspend fun fetchComments(postId: Int): TResult<List<CommentDomainModel>, PostExceptionDomainModel> {
        return runCatching {
            val apiComments = apiService.getCommentsByPostId(postId)
            val comments = apiComments.map { mapper.toDomain(it) }
            if (comments.isEmpty()) throw PostExceptionDomainModel.EmptyResponse()
            TResult.Success<List<CommentDomainModel>, PostExceptionDomainModel>(comments)
        }.getOrElse { exception ->
            TResult.Error<List<CommentDomainModel>, PostExceptionDomainModel>(
                exception.toPostExceptionDomainModel()
            )
        }
    }
}