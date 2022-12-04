package menu

import data.Priority
import data.Task
import data.TasksRepository
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(MockKExtension::class)
class ActionsKtTest {
    @MockK(relaxed = true)
    lateinit var repository: TasksRepository

    @Test
    fun addTaskFromMenuTest() {
        System.setIn("SomeName\n2".byteInputStream())
        addTaskFromMenu(repository)
        verify { repository.addTask(Task(name = "SomeName", priority = Priority.MEDIUM)) }
    }

    @Test
    fun `should execute repository get tasks without parameters`() {
        listTasksFromMenu(repository)
        verify(exactly = 1) { repository.getTasks() }
    }

    @Test
    fun `should execute repository get tasks with false parameter`() {
        listNonCompletedTasksFromMenu(repository)
        verify { repository.getTasks(false) }
    }

    @Test
    fun `repository should delete task id that will return from system input`() {
        System.setIn("3".byteInputStream())
        deleteTasksFromMenu(repository)
        verify { repository.deleteTask(3) }
    }

    @Test
    fun `repository should complete task id that will return from system input`() {
        System.setIn("3".byteInputStream())
        complete(repository)
        verify { repository.completeTask(3) }
    }

    @Test
    fun `repository should uncomplete task id that will return from system input`() {
        System.setIn("2".byteInputStream())
        uncomplete(repository)
        verify { repository.uncompleteTask(2) }
    }

}