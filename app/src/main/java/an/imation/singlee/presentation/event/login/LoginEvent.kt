package an.imation.singlee.presentation.event.login

sealed interface LoginEvent{
    data object NavigateToNextScreen : LoginEvent
    data class ShowError(val errorResID: Int): LoginEvent
}