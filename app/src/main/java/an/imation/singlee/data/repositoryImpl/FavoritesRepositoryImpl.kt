package an.imation.singlee.data.repositoryImpl

import an.imation.singlee.data.db.FavoritePostDBModel
import an.imation.singlee.data.db.FavoritePostsDao
import an.imation.singlee.domain.repository.IFavoritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.plus

class FavoritesRepositoryImpl(
    private val dao: FavoritePostsDao
) : IFavoritesRepository {
    override suspend fun addToFavorites(postId: Int) {
        dao.addFavorite(FavoritePostDBModel(postId))
    }

    override suspend fun removeFromFavorites(postId: Int) {
        dao.removeFavorite(FavoritePostDBModel(postId))
    }

    val flow = dao.getAllFavorites()
        .map { list -> list.map { it.postId } }
        .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
        .distinctUntilChanged()
        .shareIn(
            MainScope() + Dispatchers.IO,
            SharingStarted.WhileSubscribed(10_000, 0),
            replay = 1
        )

    override fun getFavorites(): Flow<List<Int>> {
        return flow
    }

    override suspend fun isFavorite(postId: Int): Boolean {
        return dao.isFavorite(postId)
    }
}