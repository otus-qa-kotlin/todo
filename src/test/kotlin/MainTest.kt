import data.Priority
import data.Task
import data.TasksRepository
import data.TasksRepositoryMemory
import io.github.serpro69.kfaker.Faker
import io.qameta.allure.Description
import kotlin.test.BeforeTest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals


class MainTest {
    private lateinit var testRepository: TasksRepository
    private val faker = Faker()

    private fun createNewTask(repository: TasksRepository, faker: Faker): Int {
        val name = faker.cowboyBebop.character()
        val priority = Priority.values().random()
        return repository.addTask(Task(name = name, priority = priority))
    }


    @BeforeTest
    fun populateTasks() {
        val tasksRepositoryMemory = TasksRepositoryMemory()
        val iterations = faker.random.nextInt(2, 5)
        for (i in 1..iterations) {
            createNewTask(tasksRepositoryMemory,faker)
        }
        testRepository = tasksRepositoryMemory
    }

    @Test
    @Description("Добавление задачи и появление ее в списке")
    fun addTaskAndCheckAppearanceTest() {
        val newTaskId = createNewTask(testRepository,faker)
        val lastIdFromTaskList = testRepository.getTasks().last().id
        assertEquals(newTaskId, lastIdFromTaskList)
    }

    @Test
    @Description("Завершить задачу и проверить корректность работы фильтра по завершенным задачам")
    fun checkFinishFilterAfterFinishTaskTest() {
        val newTaskId = createNewTask(testRepository,faker)
        testRepository.completeTask(newTaskId)
        val listOfCompletedTasks = testRepository.getTasks(completed = true).size
        assertEquals(1, listOfCompletedTasks, "Expected 1 task in filter, but got $listOfCompletedTasks")
    }

    @Test
    @Ignore
    @Description("Проверить сортировку задач по названию")
    fun checkSortingByNameTest() {
        // Sorting is not implemented yet
    }

    @Test
    @Ignore
    @Description("Проверить сортировку задач по сроку исполнения")
    fun checkSortingByDueTimeTest() {
        // Sorting is not implemented yet
    }
}