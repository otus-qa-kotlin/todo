import data.Priority
import data.Task
import data.TasksRepositoryMemory
import io.github.serpro69.kfaker.Faker
import org.junit.jupiter.api.Test
import kotlin.test.assertContains

class TodoTest {
    private val faker = Faker()

    private val tasksRepositoryMemory = TasksRepositoryMemory()
    private val task = createNewTask()

    private fun createNewTask(): Task {
        val name = faker.random.randomString(10)
        val priority = faker.random.randomValue(Priority.values())
        return Task(name = name, priority = priority)
    }

    //Сделать тест добавления задачи и появления ее в списке
    @Test
    fun testAddingTask() {
        tasksRepositoryMemory.addTask(task)
        assertContains(
            tasksRepositoryMemory.getTasks().stream().map { it.name }.toArray(), task.name
        )
    }

    //Завершить задачу и проверить корректность работы фильтра по завершенным задачам
    @Test
    fun testCompletingTask() {
        tasksRepositoryMemory.addTask(task)
        val id = faker.random.randomValue(tasksRepositoryMemory.getTasks(false)).id!!
        tasksRepositoryMemory.completeTask(id)
        val isComplete =
            tasksRepositoryMemory.getTasks(true).first { it.id == id }.completed
        assert(isComplete)
    }

    //Проверить сортировку задач по названию и сроку исполнения - сортировки нет, вместо этого проверка удаления
    @Test
    fun testDeletingTask() {
        tasksRepositoryMemory.addTask(task)
        val id = faker.random.randomValue(tasksRepositoryMemory.getTasks(false)).id!!
        tasksRepositoryMemory.deleteTask(id)
        val isDeleted = tasksRepositoryMemory.getTasks(true).none { it.id == id }
        assert(isDeleted)

    }
}