package an.imation.singlee.domain.error

sealed class PostExceptionDomainModel(exception: Throwable) : Throwable(exception) {
    override val cause: Throwable = exception

    class Other(exception: Throwable) : PostExceptionDomainModel(exception)
    class NoInternetConnection(exception: Throwable) : PostExceptionDomainModel(exception)
    class EmptyResponse(exception: Throwable = Exception("empty response")) : PostExceptionDomainModel(exception)
}
fun PostExceptionDomainModel.parseToString() = when (this) {
        is PostExceptionDomainModel.EmptyResponse -> "No comments"
        is PostExceptionDomainModel.NoInternetConnection -> "Internet connection error"
        is PostExceptionDomainModel.Other -> cause?.message ?: "Error loading comments"

}