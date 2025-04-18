package an.imation.singlee.domain.repository

import an.imation.singlee.domain.error.PostExceptionDomainModel
import an.imation.singlee.domain.error.TResult
import an.imation.singlee.domain.model.PostDomainModel

interface IPostsRepository {
    suspend fun fetchPosts(): TResult<List<PostDomainModel>, PostExceptionDomainModel>
}