package an.imation.singlee.presentation.error

import an.imation.singlee.R
import an.imation.singlee.domain.error.PostExceptionDomainModel

fun PostExceptionDomainModel.parseToString() = when (this) {
    is PostExceptionDomainModel.EmptyResponse -> R.string.error_loading
    is PostExceptionDomainModel.NoInternetConnection -> R.string.error_no_internet
    is PostExceptionDomainModel.Other ->  R.string.error_loading
}