package an.imation.singlee.presentation.ui.screen

import an.imation.singlee.destinations.DraggableBoxScreenDestination
import an.imation.singlee.destinations.LoginScreenDestination
import an.imation.singlee.destinations.PaginationScreenDestination
import an.imation.singlee.destinations.PostsScreenDestination
import an.imation.singlee.destinations.ScreenADestination
import an.imation.singlee.presentation.viewmodel.PostsViewModel
import an.imation.singlee.raamcosta.TaskIds
import an.imation.singlee.presentation.viewmodel.TasksViewModel
import an.imation.singlee.raamcosta.sharedViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun TaskListScreen(navigator: DestinationsNavigator, navController: NavController) {
    val viewModel: TasksViewModel = koinViewModel<TasksViewModel>()

    val tasks by viewModel.tasks.collectAsState()

    LazyColumn {
        items(tasks) { task ->
            Text(
                text = task.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {when (task.id) {
                        TaskIds.POSTS_TASK -> navigator.navigate(PostsScreenDestination)
                        TaskIds.LOGIN_TASK -> navigator.navigate(LoginScreenDestination)
                        TaskIds.PAGINATION_TASK -> navigator.navigate(PaginationScreenDestination)
                        TaskIds.DRAGGABLE_BOX_TASK -> navigator.navigate(DraggableBoxScreenDestination)
                        TaskIds.TASK_FIVE -> navigator.navigate(ScreenADestination)
                    }
                    }
                    .padding(16.dp)
            )
        }
    }
}