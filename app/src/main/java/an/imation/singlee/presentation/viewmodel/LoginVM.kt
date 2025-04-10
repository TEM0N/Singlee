package an.imation.singlee.presentation.viewmodel

import an.imation.singlee.R
import an.imation.singlee.presentation.event.login.LoginEvent
import an.imation.singlee.presentation.event.login.LoginIntent
import an.imation.singlee.presentation.event.login.LoginState
import an.imation.singlee.domain.usecase.LoginUseCase
import an.imation.singlee.presentation.ui.SingleFlowEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginVM(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<LoginEvent>(viewModelScope)
    val event = _event.flow

    fun sendIntent(intent: LoginIntent){
        when(intent){
            is LoginIntent.OnNextButtonClick -> viewModelScope.launch{
                _state.update{ it.copy(isLoading = true) }
                val success = loginUseCase(_state.value.userName, _state.value.password)
                if(success)
                {
                    _state.update{ it.copy(userName = "", password = "") }
                    _event.emit(LoginEvent.NavigateToNextScreen)
                } else {
                    _state.update{ it.copy(userName = "", password = "") }
                    _event.emit(LoginEvent.ShowError(R.string.invalid_credentials))
                }
                _state.update{ it.copy(isLoading = false) }
            }
            is LoginIntent.SetLogin -> _state.update{it.copy(userName = intent.login)}
            is LoginIntent.SetPassword -> _state.update{it.copy(password = intent.password)}
        }
    }
}

