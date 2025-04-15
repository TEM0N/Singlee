package an.imation.singlee.data.repositoryImpl

import an.imation.singlee.data.api.IPostApi
import an.imation.singlee.data.mapper.PostDataMapper
import an.imation.singlee.domain.model.PostDomainModel
import an.imation.singlee.domain.repository.IPostsRepository

class PostsRepositoryImpl(
    private val apiService: IPostApi,
    private val mapper: PostDataMapper,
) : IPostsRepository {
    override suspend fun fetchPosts(): List<PostDomainModel> {
        return apiService.getPosts().map { mapper.toDomain(it) }
    }
}