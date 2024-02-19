import data.Priority
import data.Task
import data.TasksRepositoryMemory
import org.junit.Test
import kotlin.test.assertEquals

class TasksRepositoryTest {
    @Test
    fun testAddTask() {
        val repository = TasksRepositoryMemory()
        val initialTasksCount = repository.getTasks().size
        repository.addTask(Task(name = "Test Task", priority = Priority.MEDIUM))
        val tasksAfterAddition = repository.getTasks()
        assertEquals(initialTasksCount + 1, tasksAfterAddition.size)
        val addedTask = tasksAfterAddition.last()
        assertEquals("Test Task", addedTask.name)
        assertEquals(Priority.MEDIUM, addedTask.priority)
    }

    @Test
    fun testCompleteTaskAndFilterCompletedTasks() {
        val repository = TasksRepositoryMemory()
        val taskId = repository.addTask(Task(name = "Test Task", priority = Priority.MEDIUM))
        repository.completeTask(taskId)
        val completedTasks = repository.getTasks(completed = true)
        assertEquals(1, completedTasks.size)
        val completedTask = completedTasks.first()
        assertEquals("Test Task", completedTask.name)
        assertEquals(true, completedTask.completed)
    }

    @Test
    fun testSortTasksByName() {
        val repository = TasksRepositoryMemory()
        repository.addTask(Task(name = "A task", priority = Priority.LOW))
        repository.addTask(Task(name = "C task", priority = Priority.MEDIUM))
        repository.addTask(Task(name = "B task", priority = Priority.HIGH))

        val sortedTasks = repository.getTasks().sortedBy { task -> task.name }

        assertEquals("A task", sortedTasks[0].name)
        assertEquals("B task", sortedTasks[1].name)
        assertEquals("C task", sortedTasks[2].name)
    }

    @Test
    fun testSortTasksByPriority() {
        val repository = TasksRepositoryMemory()
        repository.addTask(Task(name = "High Priority Task", priority = Priority.LOW))
        repository.addTask(Task(name = "Low Priority Task", priority = Priority.HIGH))
        repository.addTask(Task(name = "Medium Priority Task", priority = Priority.MEDIUM))

        val sortedTasks = repository.getTasks().sortedBy { it.priority }

        assertEquals("Low Priority Task", sortedTasks[0].name)
        assertEquals("Medium Priority Task", sortedTasks[1].name)
        assertEquals("High Priority Task", sortedTasks[2].name)

    }
}