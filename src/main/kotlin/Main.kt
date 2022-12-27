import data.TasksRepositoryMemory
import menu.taskActions

fun renderMenu(): Int {
    println("=========================================================")
    val actions = listOf(
        "Add task", "Delete task", "List all tasks", "List non-completed tasks",
        "Complete task", "Uncomplete task", "Quit"
    )
    actions.forEachIndexed { index, action ->
        println("${index + 1}. $action")
    }
    print("Make your choice: ")
    return readln().toIntOrNull() ?: 0
}

fun main() {
    println("Otus Todo List\n")
    val repository = TasksRepositoryMemory()
    while (true) {
        val action = renderMenu()
        try {
            val func = taskActions[Actions.values()[action - 1]]
            func?.call(repository)
        } catch (e: ArrayIndexOutOfBoundsException) {
            //just skip
        }
    }
}