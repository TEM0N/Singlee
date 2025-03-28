package an.imation.singlee.domain.repository

interface ILoginRepository {
    suspend fun authenticate(username: String, password: String)
}