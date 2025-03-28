package an.imation.singlee.domain.repository

import an.imation.singlee.data.model.Task

interface ITasksRepository {
    fun getTasks(): List<Task>
}