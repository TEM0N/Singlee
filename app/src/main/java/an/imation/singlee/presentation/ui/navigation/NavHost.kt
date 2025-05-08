package an.imation.singlee.presentation.ui.navigation


import an.imation.singlee.R
import an.imation.singlee.domain.model.PostDomainModel
import an.imation.singlee.presentation.source.NavigationUISource
import an.imation.singlee.presentation.ui.screen.LoginScreen
import an.imation.singlee.presentation.ui.screen.PaginationScreen
import an.imation.singlee.presentation.ui.screen.PostDetailsScreen
import an.imation.singlee.presentation.ui.screen.PostsScreen
import an.imation.singlee.presentation.ui.screen.TaskListScreen
import an.imation.singlee.presentation.viewmodel.TasksViewModel
import android.net.Uri
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
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.gson.Gson

@Composable
fun AppNavHost(navController: NavHostController, viewModel: TasksViewModel) {
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
            PostsScreen(navController)
        }
        composable(
            route = NavigationUISource.POST_DETAILS,
            arguments = listOf(navArgument("post") { type = NavType.StringType })
        ) { backStackEntry ->
            val postJson = backStackEntry.arguments?.getString("post") ?: ""
            val post = Gson().fromJson(Uri.decode(postJson), PostDomainModel::class.java)
            PostDetailsScreen(post = post)
        }
        composable(NavigationUISource.PAGINATION_SCREEN) {
            PaginationScreen(navController)
        }

    }
}