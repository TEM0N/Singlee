package an.imation.singlee.domain.usecase

import an.imation.singlee.domain.repository.ILoginRepository

class LoginUseCase(private val repository: ILoginRepository) {
    suspend operator fun invoke(username: String, password: String): Boolean {
        return try {
            repository.authenticate(username, password)
            true
        } catch (e: Exception) {
            false
        }
    }
}