package ru.otus.service

import ru.otus.data.Task
import ru.otus.data.TasksRepositoryMemory
import org.springframework.stereotype.Service
import ru.otus.http.error.*

@Service
class TaskService(private val taskRepo: TasksRepositoryMemory) {

    fun addTask(task: Task): Task {
        if (task.completed) {
            throw AddCompletedTaskException()
        }
        val id = taskRepo.addTask(task)
        task.id = id
        return task
    }

    fun deleteTask(id: Int) {
        if (id < 0) {
            throw InvalidTaskException()
        }
        taskRepo.getTasks(true).find { it.id == id }?.let {
            throw DeleteCompletedTaskException()
        }
        taskRepo.getTasks(false).find { it.id == id }?.let {
            taskRepo.deleteTask(id)
        } ?: throw TaskNotFoundException()
    }

    fun completeTask(id: Int) {
        if (id < 0) {
            throw InvalidTaskException()
        }
        taskRepo.getTasks(true).find { it.id == id }?.let {
            throw TaskCompletingError()
        }
        taskRepo.getTasks(false).find { it.id == id }?.let {
            taskRepo.completeTask(id)
        } ?: throw TaskNotFoundException()
    }

    fun uncompleteTask(id: Int) {
        if (id < 0) {
            throw InvalidTaskException()
        }
        taskRepo.getTasks(false).find { it.id == id }?.let {
            throw TaskCompletingError()
        }
        taskRepo.getTasks(true).find { it.id == id }?.let {
            taskRepo.uncompleteTask(id)
        } ?: throw  TaskNotFoundException()
    }

    fun getTasks(completed: Boolean): List<Task> {
        return taskRepo.getTasks(completed).sortedByDescending { it.priority }
    }
}