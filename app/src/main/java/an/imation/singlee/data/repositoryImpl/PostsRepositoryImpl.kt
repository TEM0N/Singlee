package an.imation.singlee.data.repositoryImpl

import an.imation.singlee.data.api.IPostApi
import an.imation.singlee.data.mapper.PostDataMapper
import an.imation.singlee.data.mapper.toPostExceptionDomainModel
import an.imation.singlee.domain.error.PostExceptionDomainModel
import an.imation.singlee.domain.error.TResult
import an.imation.singlee.domain.model.PostDomainModel
import an.imation.singlee.domain.repository.IPostsRepository
import android.util.Log

class PostsRepositoryImpl(
    private val apiService: IPostApi,
    private val mapper: PostDataMapper
) : IPostsRepository {
    override suspend fun fetchPosts(): TResult<List<PostDomainModel>, PostExceptionDomainModel> {
        return runCatching {
            val apiPosts = apiService.getPosts()
            val posts = apiPosts.mapNotNull { mapper.toDomain(it) }
            val invalid_current = apiPosts.size - posts.size
            if(invalid_current > 0){
                Log.e("!!!", "кол-во битых данных $invalid_current")
            }
            if (posts.isEmpty()) throw PostExceptionDomainModel.EmptyResponse()
            TResult.Success<List<PostDomainModel>, PostExceptionDomainModel>(posts)
        }.getOrElse { exception ->
            TResult.Error<List<PostDomainModel>, PostExceptionDomainModel>(
                exception.toPostExceptionDomainModel()
            )
        }
    }
}