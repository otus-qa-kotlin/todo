package menu

import data.Priority
import data.Task
import data.TasksRepository
import kotlin.system.exitProcess

fun addTaskFromMenu(repository: TasksRepository) {
    print("Adding task. Please enter task name: ")
    val name = readln()
    print("Choose task priority: (1-low, 2-medium, 3-high): ")
    var pr = readln().toIntOrNull() ?: 2
    pr = pr.coerceIn(1..3)
    val priority = Priority.values()[pr - 1]
    repository.addTask(Task(name = name, priority = priority))
}

fun listTasksFromMenu(repository: TasksRepository) {
    repository.getTasks().forEach(::println)
}

fun listNonCompletedTasksFromMenu(repository: TasksRepository) {
    repository.getTasks(completed = false).forEach(::println)
}

fun deleteTasksFromMenu(repository: TasksRepository) {
    print("Remove task. Please enter task id: ")
    val id = readln().toIntOrNull() ?: 0
    repository.deleteTask(id)
}

fun complete(repository: TasksRepository) {
    print("Complete task. Please enter task id: ")
    val id = readln().toIntOrNull() ?: 0
    repository.completeTask(id)
}

fun uncomplete(repository: TasksRepository) {
    print("Uncomplete task. Please enter task id: ")
    val id = readln().toIntOrNull() ?: 0
    repository.uncompleteTask(id)
}

fun quit(repository: TasksRepository) {
    exitProcess(0)
}

val taskActions = mapOf(
    Actions.ADD_TASK to ::addTaskFromMenu,
    Actions.LIST_TASKS to ::listTasksFromMenu,
    Actions.LIST_NON_COMPLETED_TASKS to ::listNonCompletedTasksFromMenu,
    Actions.DELETE_TASK to ::deleteTasksFromMenu,
    Actions.COMPLETE_TASK to ::complete,
    Actions.UNCOMPLETE_TASK to ::uncomplete,
    Actions.QUIT to ::quit,
)

