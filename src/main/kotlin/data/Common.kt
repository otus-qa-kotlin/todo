package data

enum class Priority {
    LOW,
    MEDIUM,
    HIGH
}

data class Task(val id: Int? = null, val name: String, var priority: Priority, var completed: Boolean = false) {
    override fun toString(): String = ("$id. [${if (completed) "x" else " "}] $name : ${priority}")
}

abstract class TasksRepository {
    abstract fun getTasks(completed: Boolean = true): List<Task>
    abstract fun addTask(task: Task): Int
    abstract fun deleteTask(id: Int)
    abstract fun completeTask(id: Int)
    abstract fun uncompleteTask(id: Int)
}
