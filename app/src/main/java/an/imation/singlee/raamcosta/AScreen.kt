package an.imation.singlee.raamcosta

import an.imation.singlee.NavGraphs
import an.imation.singlee.destinations.ScreenCDestination
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.result.ResultRecipient


@RootNavGraph
@NavGraph
annotation class NestedGraphNavGraph(
    val start: Boolean = false
)

@Destination
@Composable
fun ScreenA(
    navigator: DestinationsNavigator
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Экран A")
        Button(onClick = {
            navigator.navigate(NavGraphs.nestedGraph)
        }) {
            Text("Перейти к B")
        }
    }
}

@NestedGraphNavGraph(start = true)
@Destination
@Composable
fun ScreenB(
    navigator: DestinationsNavigator,
    navController: NavController,
    resultRecipient: ResultRecipient<ScreenCDestination, String>
) {
    val sharedVM: SharedViewModel = navController.sharedViewModel()

    resultRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> Log.d("ScreenB", "Возврат без результата")
            is NavResult.Value -> Log.d("ScreenB", "Получен результат: ${result.value}")
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Экран B")
        Button(onClick = {
            navigator.navigate(ScreenCDestination)
        }) {
            Text("Перейти к C")
        }
    }
}

@NestedGraphNavGraph
@Destination
@Composable
fun ScreenC(
    resultNavigator: ResultBackNavigator<String>,
    navController: NavController,
    navigator: DestinationsNavigator
) {
    val sharedVM: SharedViewModel = navController.sharedViewModel()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Экран C")

        Button(onClick = {
            val randomString = List(10) {
                (('a'..'z') + ('A'..'Z') + ('0'..'9')).random()
            }.joinToString("")

            resultNavigator.navigateBack(randomString)
        }) {
            Text("Вернуть случайную строку")
        }

        Button(onClick = {
            navigator.popBackStack(
                route = NavGraphs.nestedGraph.route,
                inclusive = true
            )
        }) {
            Text("Закрыть граф B-C")
        }
    }
}