package an.imation.singlee.domain.error

sealed class PostExceptionDomainModel(ex: Throwable) : Throwable(ex) {
    override val cause: Throwable = ex

    class Other(ex: Throwable) : PostExceptionDomainModel(ex)
    class NoInternetConnection(ex: Throwable) : PostExceptionDomainModel(ex)
}
