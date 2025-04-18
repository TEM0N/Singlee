package an.imation.singlee.domain.error

import an.imation.singlee.R

sealed class PostExceptionDomainModel(exception: Throwable) : Throwable(exception) {
    override val cause: Throwable = exception

    class Other(exception: Throwable) : PostExceptionDomainModel(exception)
    class NoInternetConnection(exception: Throwable) : PostExceptionDomainModel(exception)
    class EmptyResponse(exception: Throwable = Exception("empty response")) : PostExceptionDomainModel(exception)
}
