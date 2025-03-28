package an.imation.singlee.presentation.ui

import an.imation.singlee.presentation.viewmodel.TasksViewModel
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
@Composable
fun TaskListScreen(viewModel: TasksViewModel, onTaskClick: (Int) -> Unit) {
    val tasks by viewModel.tasks.collectAsState()

    LazyColumn {
        items(tasks) { task ->
            Text(
                text = task.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onTaskClick(task.id) }
                    .padding(16.dp)
            )
        }
    }
}