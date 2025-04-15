package an.imation.singlee.presentation.viewmodel

import an.imation.singlee.data.model.TaskDataModel
import an.imation.singlee.domain.usecase.FetchTasksUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TasksViewModel(private val fetchTasksUseCase: FetchTasksUseCase) : ViewModel() {
    private val _tasks = MutableStateFlow<List<TaskDataModel>>(listOf())
    val tasks = _tasks.asStateFlow()

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = fetchTasksUseCase.invoke()
        }
    }
}