package an.imation.singlee.data.repositoryImpl

import an.imation.singlee.data.model.Task
import an.imation.singlee.domain.repository.ITasksRepository

class TasksRepositoryImpl : ITasksRepository {
    private val _tasks = mutableListOf<Task>()

    init {
        loadTasks()
    }

    override fun getTasks(): List<Task> = _tasks

    private fun loadTasks() {
        _tasks.add(Task(1, "Задание 1"))

    }
}