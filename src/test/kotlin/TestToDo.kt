import data.Priority
import data.Task
import data.TasksRepositoryMemory
import org.junit.jupiter.api.Assertions.assertTrue

//генератор тестовых тасок
fun generateTestTask(isCompleted: Boolean): Task {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    val taskName = (1..8)
        .map { allowedChars.random() }
        .joinToString("")
    val priority = Priority.values()[(0..2).random()]
    return Task(name= taskName, priority = priority, completed = isCompleted)
}
//Сделать тест добавления задачи и появления ее в списке
//Завершить задачу и проверить корректность работы фильтра по завершенным задачам

//Сформировать отчет с использованием библиотеки Allure Kotlin
//Результатом должен быть код теста, который заполняет пустой список задач
//тестовыми данными и проверяет корректность текущего состояния списка при выполнении
fun verifyIfTaskInList(taskList: TasksRepositoryMemory, testTask: Task, isCompleted: Boolean) {
    val filteredTaskList = taskList.getTasks(isCompleted).filter { it.name == testTask.name}
    filteredTaskList.forEach(::println)
    println(testTask)
    assertTrue(filteredTaskList.isNotEmpty())
}

fun createNUncompletedTasks(n: Int) :TasksRepositoryMemory{
    val taskList = TasksRepositoryMemory()
    //создадим список из n заданий
    var i = n
    while (i>0) {
        taskList.addTask(generateTestTask(false))
        i--
    }
    return taskList
}

