package an.imation.singlee.domain.usecase

import an.imation.singlee.data.model.Task
import an.imation.singlee.domain.repository.ITasksRepository

class FetchTasksUseCase(private val repository: ITasksRepository) {
    fun invoke(): List<Task> = repository.getTasks()
}