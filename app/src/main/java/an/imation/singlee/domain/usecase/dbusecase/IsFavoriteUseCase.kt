package an.imation.singlee.domain.usecase.dbusecase

import an.imation.singlee.domain.repository.IFavoritesRepository

class IsFavoriteUseCase(private val repository: IFavoritesRepository) {
    suspend operator fun invoke(postId: Int): Boolean = repository.isFavorite(postId)
}