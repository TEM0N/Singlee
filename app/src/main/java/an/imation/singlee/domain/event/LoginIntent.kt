package an.imation.singlee.domain.event

sealed interface LoginIntent{
    data class SetLogin(val login: String) : LoginIntent
    data class SetPassword(val password: String) : LoginIntent
    class OnNextButtonClick: LoginIntent
}