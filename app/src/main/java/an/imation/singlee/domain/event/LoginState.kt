package an.imation.singlee.domain.event

data class LoginState(
    val userName: String = "",
    val password: String = "",
    val errorResID: Int? = null,
    val isLoading: Boolean = false
){
    val isEnableNextButton = userName.isNotEmpty() && password.isNotEmpty()
}