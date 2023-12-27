package tests.serenityBDD

import data.Task
import helpers.TestDataRepository
import net.serenitybdd.annotations.Steps
import net.serenitybdd.junit5.SerenityJUnit5Extension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.streams.toList


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SerenityJUnit5Extension::class)
class TasksRepositoryMemoryTests {
    @AfterEach
    fun cleanRepository() {
        repository.clearRepository()
    }

    private val testDataRepository = TestDataRepository()

    private fun getStreamOfTestTasks(): Stream<Task> {
        return testDataRepository.testTasks.stream()
    }

    @Steps
    lateinit var repository: TasksRepositoryMemorySteps

    @ParameterizedTest
    @MethodSource("getStreamOfTestTasks")
    fun addTask(task: Task) {
        val addedTask: Task = repository.addTask(task)
        repository.checkIfTaskInRepository(addedTask)
    }

    @Test
    fun checkFilterOfTasksIncludingCompleted() {
        val tasks: List<Task> = getStreamOfTestTasks().toList()

        val tasksAfterAdding = repository.addGroupOfTasks(tasks)
        val indexOfCompletedTask = repository.completeRandomTask(tasksAfterAdding)
        val completedTask = tasksAfterAdding[indexOfCompletedTask]
        repository.checkFilterOfTasksIncludingCompleted(completedTask)
    }

    @Test
    fun checkFilterOfTasksExcludingCompleted() {
        val tasks: List<Task> = getStreamOfTestTasks().toList()

        val tasksAfterAdding = repository.addGroupOfTasks(tasks)
        val indexOfCompletedTask = repository.completeRandomTask(tasksAfterAdding)
        val completedTask = tasksAfterAdding[indexOfCompletedTask]
        repository.checkFilterOfTasksExcludingCompleted(completedTask)
    }

    @Test
    fun deleteTaskFromRepository() {
        val tasks: List<Task> = getStreamOfTestTasks().toList()
        val tasksAfterAdding = repository.addGroupOfTasks(tasks)
        val indexOfDeletedTask = repository.deleteTask(tasksAfterAdding)
        val deletedTask = tasksAfterAdding[indexOfDeletedTask]
        repository.checkIfDeletedTaskNotInRepository(deletedTask)
    }

    @Test
    fun uncompleteTask() {
        val tasks: List<Task> = getStreamOfTestTasks().toList()

        val tasksAfterAdding = repository.addGroupOfTasks(tasks)
        val indexOfCompletedTask = repository.completeRandomTask(tasksAfterAdding)
        val uncompletedTask = tasksAfterAdding[indexOfCompletedTask]
        repository.uncompleteTask(indexOfCompletedTask)
        repository.checkIfTaskUncompleted(uncompletedTask)
    }
}