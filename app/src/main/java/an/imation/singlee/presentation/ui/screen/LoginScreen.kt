package an.imation.singlee.presentation.ui.screen

import an.imation.singlee.R
import an.imation.singlee.presentation.event.login.LoginEvent
import an.imation.singlee.presentation.event.login.LoginIntent
import an.imation.singlee.presentation.viewmodel.LoginVM
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.androidx.compose.koinViewModel
import an.imation.singlee.presentation.event.login.LoginState
import an.imation.singlee.presentation.source.NavigationUISource

fun NavController.navigateToLoginScreen() = navigate(
    NavigationUISource.LOGIN)

@Composable
fun LoginScreen(navController: NavController) {
    val viewmodel = koinViewModel<LoginVM>()
    val state by viewmodel.state.collectAsStateWithLifecycle()
    val intent by remember { mutableStateOf(viewmodel::sendIntent)}
    val event by remember { mutableStateOf(viewmodel.event) }

    UI(
        state = state,
        intent = intent
    )

    LaunchedEffect(Unit){
        event.filterIsInstance<LoginEvent.NavigateToNextScreen>().collect {
            navController.navigate(NavigationUISource.EMPTY_SCREEN)
        }
    }

    val context = LocalContext.current
    LaunchedEffect(Unit){
        event.filterIsInstance<LoginEvent.ShowError>().collect {
            Toast.makeText(context, it.errorResID, Toast.LENGTH_SHORT).show()
        }
    }
}


@Composable
@Preview
private  fun UI(
    state: LoginState = LoginState(),
    intent: (LoginIntent) -> Unit = {}
){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            Column(modifier = Modifier.padding(16.dp)) {
                TextField(
                    value = state.userName,
                    onValueChange = { intent(LoginIntent.SetLogin(it)) },
                    label = { Text(stringResource(id = R.string.login)) }
                )
                TextField(
                    value = state.password,
                    onValueChange = { intent(LoginIntent.SetPassword(it)) },
                    label = { Text(stringResource(id = R.string.password)) }
                )

                Button(
                    onClick = { intent(LoginIntent.OnNextButtonClick()) },
                    enabled = state.isEnableNextButton
                ) {
                    Text(stringResource(id = R.string.login_button))
                }

            }
        }
    }
}