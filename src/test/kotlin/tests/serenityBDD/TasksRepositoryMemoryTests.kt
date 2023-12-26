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
        steps.clearRepository()
    }

    private val testDataRepository = TestDataRepository()

    private fun getStreamOfTestTasks(): Stream<Task> {
        return testDataRepository.testTasks.stream()
    }

    @Steps
    lateinit var steps: TasksRepositoryMemorySteps

    @ParameterizedTest
    @MethodSource("getStreamOfTestTasks")
    fun addTask(task: Task) {
        val addedTask: Task = steps.addTask(task)
        steps.checkIfTaskInRepository(addedTask)
    }

    @Test
    fun checkFilterOfTasksIncludingCompleted() {
        val tasks: List<Task> = getStreamOfTestTasks().toList()

        val tasksAfterAdding = steps.addGroupOfTasks(tasks)
        val indexOfCompletedTask = steps.completeRandomTask(tasksAfterAdding)
        val completedTask = tasksAfterAdding[indexOfCompletedTask]
        steps.checkFilterOfTasksIncludingCompleted(completedTask)
    }

    @Test
    fun checkFilterOfTasksExcludingCompleted() {
        val tasks: List<Task> = getStreamOfTestTasks().toList()

        val tasksAfterAdding = steps.addGroupOfTasks(tasks)
        val indexOfCompletedTask = steps.completeRandomTask(tasksAfterAdding)
        val completedTask = tasksAfterAdding[indexOfCompletedTask]
        steps.checkFilterOfTasksExcludingCompleted(completedTask)
    }

    @Test
    fun deleteTaskFromRepository() {
        val tasks: List<Task> = getStreamOfTestTasks().toList()
        val tasksAfterAdding = steps.addGroupOfTasks(tasks)
        val indexOfDeletedTask = steps.deleteTask(tasksAfterAdding)
        val deletedTask = tasksAfterAdding[indexOfDeletedTask]
        steps.checkIfDeletedTaskNotInRepository(deletedTask)
    }

    @Test
    fun uncompleteTask() {
        val tasks: List<Task> = getStreamOfTestTasks().toList()

        val tasksAfterAdding = steps.addGroupOfTasks(tasks)
        val indexOfCompletedTask = steps.completeRandomTask(tasksAfterAdding)
        val uncompletedTask = tasksAfterAdding[indexOfCompletedTask]
        steps.uncompleteTask(indexOfCompletedTask)
        steps.checkIfTaskUncompleted(uncompletedTask)
    }
}

