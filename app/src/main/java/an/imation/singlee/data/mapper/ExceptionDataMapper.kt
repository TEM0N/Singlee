package an.imation.singlee.data.mapper

import an.imation.singlee.domain.error.PostExceptionDomainModel
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.toPostExceptionDomainModel():  PostExceptionDomainModel {
    return when {
        this is SocketTimeoutException ->
            PostExceptionDomainModel.NoInternetConnection(this)

        this is ConnectException ->
            PostExceptionDomainModel.NoInternetConnection(this)

        this is UnknownHostException ->
            PostExceptionDomainModel.NoInternetConnection(this)

        this is java.net.SocketException &&
                this.message?.contains("Network is unreachable") == true ->
            PostExceptionDomainModel.NoInternetConnection(this)

        else -> PostExceptionDomainModel.Other(this)
    }
}