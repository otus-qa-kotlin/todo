package menu

import data.Priority
import data.Task
import data.TasksRepositoryMemory
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ActionsKtTest {

    private val taskTestRepository : TasksRepositoryMemory by lazy { TasksRepositoryMemory() }
    @Test
    fun addTaskFromMenu() {
        taskTestRepository.addTask(Task(name = "new task", priority = Priority.MEDIUM))
        assertContains(taskTestRepository.tasks.stream().map { task -> task.name }.toArray(), "new task")
    }

    @Test
    fun completeTaskAndFilter() {
        taskTestRepository.tasks.add(Task(id = 1, name = "Completed task", priority = Priority.HIGH, completed = false))
        taskTestRepository.completeTask(1)
        assertEquals(
            true,
            taskTestRepository.tasks.first { task -> task.name == "Completed task" }.completed
        )

        taskTestRepository.getTasks(completed = true).let {
                list ->
            assertAll("Check completed task filtered",
                { assertEquals(1, list.size) },
                { assertEquals("Completed task", list[0].name) })
        }
    }

    @Test
    fun deleteTask() {
        taskTestRepository.tasks.add(Task(id=1, name="Deleted task", priority = Priority.MEDIUM, completed = false))
        taskTestRepository.tasks.add(Task(id=2, name="Not Deleted task", priority = Priority.HIGH, completed = false))

        taskTestRepository.deleteTask(1)

        assertAll("Check task Repository after task removal",
            { assertEquals(1, taskTestRepository.tasks.size) },
            { assertEquals("Not Deleted task", taskTestRepository.tasks[0].name) })
    }

    @Test
    fun completeAndUncompleteTask() {
        taskTestRepository.tasks.add(Task(id = 1, name = "Uncompleted task", priority = Priority.HIGH, completed = false))
        taskTestRepository.completeTask(1)
        assertEquals(
            true,
            taskTestRepository.tasks.first { task -> task.name == "Uncompleted task" }.completed
        )

        taskTestRepository.uncompleteTask(1)
        assertEquals(
            false,
            taskTestRepository.tasks.first { task -> task.name == "Uncompleted task" }.completed
        )
    }
}