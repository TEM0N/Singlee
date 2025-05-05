package an.imation.singlee.domain.usecase.dbusecase

import an.imation.singlee.domain.repository.IFavoritesRepository

class RemoveFromFavoritesUseCase(private val repository: IFavoritesRepository) {
    suspend operator fun invoke(postId: Int) = repository.removeFromFavorites(postId)
}