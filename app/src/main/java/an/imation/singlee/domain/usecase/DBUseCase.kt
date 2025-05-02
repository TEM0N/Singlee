package an.imation.singlee.domain.usecase

import an.imation.singlee.domain.repository.IFavoritesRepository
import kotlinx.coroutines.flow.Flow

class AddToFavoritesUseCase(private val repository: IFavoritesRepository) {
    suspend operator fun invoke(postId: Int) = repository.addToFavorites(postId)
}

class RemoveFromFavoritesUseCase(private val repository: IFavoritesRepository) {
    suspend operator fun invoke(postId: Int) = repository.removeFromFavorites(postId)
}

class GetFavoritesUseCase(private val repository: IFavoritesRepository) {
    operator fun invoke(): Flow<List<Int>> = repository.getFavorites()
}

class IsFavoriteUseCase(private val repository: IFavoritesRepository) {
    suspend operator fun invoke(postId: Int): Boolean = repository.isFavorite(postId)
}