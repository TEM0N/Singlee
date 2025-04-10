package an.imation.singlee.domain.repository

import an.imation.singlee.data.model.TaskDataModel

interface ITasksRepository {
    fun getTasks(): List<TaskDataModel>
}