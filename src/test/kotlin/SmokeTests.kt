import data.Priority
import data.Task
import data.TasksRepositoryMemory
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class SmokeTests {
    @Test
    fun addTask() {
        val repository = TasksRepositoryMemory()
        repository.addTask(Task(name = "Test", priority = Priority.LOW))
        val list = repository.getTasks()
        list.forEach{
            it.name.shouldBe("Test")
            it.priority.shouldBe(Priority.LOW)
        }
    }

    @Test
    fun finishTask() {
        val repository = TasksRepositoryMemory()
        val id = repository.addTask(Task(name = "Test", priority = Priority.LOW))
        repository.completeTask(id)
        val list = repository.getTasks(true)
        list.forEach{
            it.name.shouldBe("Test")
            it.priority.shouldBe(Priority.LOW)
        }
    }

    @Test
    fun sortTasks() {
        val repository = TasksRepositoryMemory()
        repository.addTask(Task(name = "Test", priority = Priority.LOW))
        repository.addTask(Task(name = "Test2", priority = Priority.HIGH))

        val list = repository.getTasks()
        list[0].name.shouldBe("Test")
        list[0].priority.shouldBe(Priority.LOW)

        list[1].name.shouldBe("Test2")
        list[1].priority.shouldBe(Priority.HIGH)}

    }

