package an.imation.singlee.domain.repository

import kotlinx.coroutines.flow.Flow

interface IFavoritesRepository {
    suspend fun addToFavorites(postId: Int)
    suspend fun removeFromFavorites(postId: Int)
    fun getFavorites(): Flow<List<Int>>
    suspend fun isFavorite(postId: Int): Boolean
}
