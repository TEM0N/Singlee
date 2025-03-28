package an.imation.singlee.data.repositoryImpl

import an.imation.singlee.domain.repository.ILoginRepository
import kotlinx.coroutines.delay

class LoginRepositoryImpl : ILoginRepository {
    private val validCredentials = listOf(
        Pair("admin", "password"),
        Pair("user", "1234")
    )

    override suspend fun authenticate(username: String, password: String) {
        delay(1500)
        if (!validCredentials.contains(Pair(username, password))) {
            throw Exception("Неверный логин или пароль")
        }
    }
}