package an.imation.singlee.data.repositoryImpl

import an.imation.singlee.data.model.TaskDataModel
import an.imation.singlee.domain.repository.ITasksRepository

class TasksRepositoryImpl : ITasksRepository {
    private val _tasks = mutableListOf<TaskDataModel>()

    init {
        loadTasks()
    }

    override fun getTasks(): List<TaskDataModel> = _tasks

    private fun loadTasks() {
        _tasks.add(TaskDataModel(1, "Задание 1"))
        _tasks.add(TaskDataModel(2, "Задание 2"))
        _tasks.add(TaskDataModel(3, "Задание 3"))
    }
}