package an.imation.singlee.data.repositoryImpl

import an.imation.singlee.data.db.FavoritePostEntity
import an.imation.singlee.data.db.FavoritePostsDao
import an.imation.singlee.domain.repository.IFavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesRepositoryImpl(
    private val dao: FavoritePostsDao
) : IFavoritesRepository {
    override suspend fun addToFavorites(postId: Int) {
        dao.addFavorite(FavoritePostEntity(postId))
    }

    override suspend fun removeFromFavorites(postId: Int) {
        dao.removeFavorite(FavoritePostEntity(postId))
    }

    override fun getFavorites(): Flow<List<Int>> {
        return dao.getAllFavorites().map { list -> list.map { it.postId } }
    }

    override suspend fun isFavorite(postId: Int): Boolean {
        return dao.isFavorite(postId)
    }
}