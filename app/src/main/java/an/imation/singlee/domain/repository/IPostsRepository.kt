package an.imation.singlee.domain.repository

import an.imation.singlee.domain.model.PostDomainModel

interface IPostsRepository {
    suspend fun fetchPosts(): List<PostDomainModel>
}