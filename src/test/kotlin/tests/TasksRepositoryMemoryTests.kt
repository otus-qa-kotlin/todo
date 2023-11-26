package tests

import data.Task
import data.TasksRepositoryMemory
import helpers.TestDataRepository
import io.qameta.allure.Allure.step
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.random.Random
import kotlin.streams.toList
import kotlin.test.assertContains
import kotlin.test.assertFalse


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TasksRepositoryMemoryTests {
    private val testDataRepository = TestDataRepository()

    private fun getStreamOfTestTasks(): Stream<Task> {
        return testDataRepository.testTasks.stream()
    }

    @ParameterizedTest
    @MethodSource("getStreamOfTestTasks")
    fun addTask(task: Task) {
        val repository = TasksRepositoryMemory()
        var addedTask: Task = task
        step {
            it.name("Add a task to the repository")
            val taskId = repository.addTask(task)
            addedTask = task.copy(id = taskId)
        }
        step {
            it.name("Check if added task is in the repository")
            assertContains(repository.tasks, addedTask, "The task in not in the repository")
        }
    }

    @Test
    fun checkFilterOfTasksIncludingCompleted() {
        val repository = TasksRepositoryMemory()
        val tasks: List<Task> = getStreamOfTestTasks().toList()
        val idOfCompletedTask = Random.nextInt(1, tasks.size)
        val indexOfCompletedTask = idOfCompletedTask - 1
        val tasksAfterAdding = mutableListOf<Task>()

        step {
            it.name("Add a group of tasks to the repository")
            tasks.forEach { task ->
                val taskId = repository.addTask(task)
                tasksAfterAdding.add(task.copy(id = taskId))
            }
        }
        step {
            it.name("Complete a random task")
            repository.completeTask(idOfCompletedTask)
            tasksAfterAdding[indexOfCompletedTask].completed = true
        }
        step {
            it.name("Check filter of tasks including completed")
            assertContains(repository.getTasks(true), tasksAfterAdding[indexOfCompletedTask], "The task in not in the repository")
        }
    }

    @Test
    fun checkFilterOfTasksExcludingCompleted() {
        val repository = TasksRepositoryMemory()
        val tasks: List<Task> = testDataRepository.testTasks
        val idOfCompletedTask: Int = Random.nextInt(1, tasks.size)
        val indexOfCompletedTask = idOfCompletedTask - 1
        val tasksAfterAdding = mutableListOf<Task>()

        step {
            it.name("Add a group of tasks to the TasksRepository")
            tasks.forEach { task ->
                val taskId = repository.addTask(task)
                tasksAfterAdding.add(task.copy(id = taskId))
            }
        }
        step {
            it.name("Complete a task")
            repository.completeTask(idOfCompletedTask)
            tasksAfterAdding[indexOfCompletedTask].completed = true
        }
        step {
            it.name("Check filter of tasks excluding completed")
            assertFalse {
                repository.getTasks(false).contains(tasksAfterAdding[indexOfCompletedTask])
            }
        }
    }

    @Test
    fun deleteTaskFromRepository() {
        val repository = TasksRepositoryMemory()
        val tasks: List<Task> = testDataRepository.testTasks
        val idOfDeletedTask: Int = Random.nextInt(1, tasks.size)
        val indexOfDeletedTask = idOfDeletedTask - 1
        val tasksAfterAdding = mutableListOf<Task>()

        step {
            it.name("Add a group of tasks to the TasksRepository")
            tasks.forEach { task ->
                val taskId = repository.addTask(task)
                tasksAfterAdding.add(task.copy(id = taskId))
            }
        }
        step {
            it.name("Delete a task")
            repository.deleteTask(idOfDeletedTask)
        }
        step {
            it.name("Check if deleted task is not in the repository")
            assertFalse {
                repository.getTasks(false).contains(tasksAfterAdding[indexOfDeletedTask])
            }
        }
    }

    @Test
    fun uncompleteTask() {
        val repository = TasksRepositoryMemory()
        val tasks: List<Task> = testDataRepository.testTasks
        val idOfCompletedTask: Int = Random.nextInt(1, tasks.size)
        val indexOfCompletedTask = idOfCompletedTask - 1
        val tasksAfterAdding = mutableListOf<Task>()

        step {
            it.name("Add a group of tasks to the repository")
            tasks.forEach { task ->
                val taskId = repository.addTask(task)
                tasksAfterAdding.add(task.copy(id = taskId))
            }
        }
        step {
            it.name("Complete a random task")
            repository.completeTask(idOfCompletedTask)
        }
        step {
            it.name("Uncomplete the task")
            repository.uncompleteTask(idOfCompletedTask)
        }
        step {
            it.name("Check if the task is uncompleted")
            assertContains(repository.getTasks(true), tasksAfterAdding[indexOfCompletedTask])

        }
    }
}

