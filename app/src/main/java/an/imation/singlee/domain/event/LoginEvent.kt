package an.imation.singlee.domain.event

sealed interface LoginEvent{
    data object NavigateToNextScreen : LoginEvent
    data class ShowError(val errorResID: Int): LoginEvent
}