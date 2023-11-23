/*
1) Сделать тест добавления задачи и появления ее в списке
2) Завершить задачу и проверить корректность работы фильтра по завершенным задачам
3) Проверить сортировку задач по названию и сроку исполнения
4) Сформировать отчет с использованием библиотеки Allure Kotlin

Результатом должен быть код теста, который заполняет пустой список задач тестовыми данными
и проверяет корректность текущего состояния списка при выполнении
 */

import data.Priority
import data.Task
import data.TasksRepositoryMemory
import io.github.serpro69.kfaker.Faker
import jdk.jfr.Description
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@Description("Тест приложения списка задач")
class MainTest {
    private var tasks  = TasksRepositoryMemory()

    private val faker = Faker()
    private var name = faker.theOffice.quotes()
    private var priority = Priority.values().random()

    private val testTask = Task( name = name, priority = priority)

    @Description("Сделать тест добавления задачи и появления ее в списке")
    @Test
    fun testAddTask() {
        tasks.addTask(testTask)
        assertEquals(name, tasks.getTasks().first().name)
    }

    @Description("Завершить задачу и проверить корректность работы фильтра по завершенным задачам")
    @Test
    fun testCompleteTask() {
        val taskId = tasks.addTask(testTask)
        tasks.completeTask(taskId)
        assertEquals(true, tasks.getTasks(completed = true).isNotEmpty())
    }

    @Description("Проверить сортировку задач по названию и сроку исполнения")
    @Test
    fun testSortTask() {
        // непонятно что проверять, в исходном приложении нет методов сортировки тасок
    }
}


