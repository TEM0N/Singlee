package an.imation.singlee.domain.usecase.dbusecase

import an.imation.singlee.domain.repository.IFavoritesRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCase(private val repository: IFavoritesRepository) {
    operator fun invoke(): Flow<List<Int>> = repository.getFavorites()
}