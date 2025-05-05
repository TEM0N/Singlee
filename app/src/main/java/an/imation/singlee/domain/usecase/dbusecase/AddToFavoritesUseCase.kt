package an.imation.singlee.domain.usecase.dbusecase

import an.imation.singlee.domain.repository.IFavoritesRepository

class AddToFavoritesUseCase(private val repository: IFavoritesRepository) {
    suspend operator fun invoke(postId: Int) = repository.addToFavorites(postId)
}