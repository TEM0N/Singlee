package an.imation.singlee.domain.usecase

import an.imation.singlee.data.mapper.toPostExceptionDomainModel
import an.imation.singlee.domain.error.PostExceptionDomainModel
import an.imation.singlee.domain.error.TResult
import an.imation.singlee.domain.model.PostDomainModel
import an.imation.singlee.domain.repository.IPostsRepository

class FetchPostsUseCase(
    private val repository: IPostsRepository
) {
    suspend operator fun invoke(): TResult<List<PostDomainModel>, PostExceptionDomainModel> {
        return runCatching {
            val posts = repository.fetchPosts()
            if (posts.isEmpty()) throw PostExceptionDomainModel.EmptyResponse()
            TResult.Success<List<PostDomainModel>, PostExceptionDomainModel>(posts)
        }.getOrElse {
            TResult.Error(it.toPostExceptionDomainModel())
        }
    }
}