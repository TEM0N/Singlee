package an.imation.singlee.presentation.ui


import an.imation.singlee.R
import an.imation.singlee.presentation.NavigationConstants
import an.imation.singlee.presentation.viewmodel.TasksViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource



@Composable
fun AppNavHost(navController: NavHostController, viewModel: TasksViewModel) {
    NavHost(navController, startDestination = NavigationConstants.TASK_LIST) {
        composable(NavigationConstants.TASK_LIST) {
            TaskListScreen(viewModel, onTaskClick = { navController.navigateToLoginScreen() })
        }
        composable(NavigationConstants.LOGIN) {
            LoginScreen(navController)
        }
        composable(NavigationConstants.EMPTY_SCREEN) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(stringResource(id = R.string.welcome_message))
            }
        }
    }
}