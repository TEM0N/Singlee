package an.imation.singlee.presentation.ui.navigation


import an.imation.singlee.R
import an.imation.singlee.presentation.source.NavigationUISource
import an.imation.singlee.presentation.ui.screen.LoginScreen
import an.imation.singlee.presentation.ui.screen.PostsScreen
import an.imation.singlee.presentation.ui.screen.TaskListScreen
import an.imation.singlee.presentation.viewmodel.TasksVM
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
fun AppNavHost(navController: NavHostController, viewModel: TasksVM) {
    NavHost(navController, startDestination = NavigationUISource.TASK_LIST) {
        composable(NavigationUISource.TASK_LIST) {
            TaskListScreen(viewModel, onTaskClick = { taskId ->  navController.handleTaskClick(taskId)
            })
        }
        composable(NavigationUISource.LOGIN) {
            LoginScreen(navController)
        }
        composable(NavigationUISource.EMPTY_SCREEN) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(stringResource(id = R.string.welcome_message))
            }
        }
        composable(NavigationUISource.POSTS_SCREEN) {
            PostsScreen()
        }
    }
}