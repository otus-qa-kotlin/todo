package tests.cucumber.steps

import data.Task
import data.TasksRepositoryMemory
import helpers.TestDataRepository
import io.cucumber.java8.En
import org.junit.jupiter.api.Assertions
import java.util.stream.Stream
import kotlin.random.Random
import kotlin.streams.toList
import kotlin.test.assertContains

class TasksRepositoryMemorySteps: En {
    init {
        val testDataRepository = TestDataRepository()
        lateinit var addedTask: Task

        fun getStreamOfTestTasks(): Stream<Task> {
            return testDataRepository.testTasks.stream()
        }
        lateinit var repository: TasksRepositoryMemory

        Given("Init a repository") {
            repository = TasksRepositoryMemory()
        }

        When("Add a task to the repository") {
            repository.tasks.clear()
            val task = getStreamOfTestTasks().toList().first()
            val taskId = repository.addTask(task)
            addedTask = task.copy(id = taskId)
        }

        When("Remove the task from the repo") {
            repository.deleteTask(addedTask.id!!)
        }

        Then("Verify the task is present in the repository") {
            assertContains(repository.tasks, addedTask, "The task in not in the repository")
        }

        Then("Verify the task is not present in the repository") {
            Assertions.assertFalse {
                repository.getTasks(false).contains(addedTask)
            }
        }
    }
}

