package an.imation.singlee.domain.usecase

import an.imation.singlee.data.model.TaskDataModel
import an.imation.singlee.domain.repository.ITasksRepository

class FetchTasksUseCase(private val repository: ITasksRepository) {
    fun invoke(): List<TaskDataModel> = repository.getTasks()
}