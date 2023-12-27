package tests.serenityBDD

import data.Task
import data.TasksRepositoryMemory
import net.serenitybdd.annotations.Step
import org.junit.jupiter.api.Assertions.assertFalse
import kotlin.random.Random
import kotlin.test.assertContains

open class TasksRepositoryMemorySteps {
    private val repository = TasksRepositoryMemory()
    lateinit var actor: String

    fun clearRepository() {
        repository.tasks.clear()
    }
    @Step("#actor add a task to the repository")
    open fun addTask(task: Task): Task {
        val taskId = repository.addTask(task)
        return task.copy(id = taskId)
    }

    @Step("#actor check if added task is in the repository")
    open fun checkIfTaskInRepository(task: Task) {
        assertContains(repository.tasks, task, "The task in not in the repository")
    }

    @Step("#actor add a group of tasks to the repository")
    open fun addGroupOfTasks(tasks: List<Task>): List<Task> {
        val tasksAfterAdding = mutableListOf<Task>()
        tasks.forEach { task ->
            val taskId = repository.addTask(task)
            tasksAfterAdding.add(task.copy(id = taskId))
        }
        return tasksAfterAdding
    }

    @Step("#actor complete a random task")
    open fun completeRandomTask(tasksAfterAdding: List<Task>): Int {
        val idOfCompletedTask = Random.nextInt(1, tasksAfterAdding.size)
        val indexOfCompletedTask = idOfCompletedTask - 1
        repository.completeTask(idOfCompletedTask)
        tasksAfterAdding[indexOfCompletedTask].completed = true
        return indexOfCompletedTask
    }

    @Step("#actor check filter of tasks including completed")
    open fun checkFilterOfTasksIncludingCompleted(task: Task) {
        assertContains(repository.getTasks(true), task, "The task in not in the repository")
    }

    @Step("#actor check filter of tasks excluding completed")
    open fun checkFilterOfTasksExcludingCompleted(task: Task) {
        assertFalse {
            repository.getTasks(false).contains(task)
        }
    }

    @Step("#actor delete a task")
    open fun deleteTask(tasksAfterAdding: List<Task>): Int {
        val idOfDeletedTask: Int = Random.nextInt(1, tasksAfterAdding.size)
        val indexOfDeletedTask = idOfDeletedTask - 1
        repository.deleteTask(idOfDeletedTask)
        return indexOfDeletedTask
    }

    @Step("#actor check if deleted task is not in the repository")
    open fun checkIfDeletedTaskNotInRepository(task: Task) {
        assertFalse {
            repository.getTasks(false).contains(task)
        }
    }

    @Step("#actor uncomplete the task")
    open fun uncompleteTask(idOfCompletedTask: Int){
        repository.uncompleteTask(idOfCompletedTask)
    }

    @Step("#actor check if the task is uncompleted")
    open fun checkIfTaskUncompleted(task: Task){
        assertContains(repository.getTasks(true), task)
    }

    override fun toString(): String {
        return actor
    }
}

