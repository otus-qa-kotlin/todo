package ru.otus.data

import com.fasterxml.jackson.annotation.JsonProperty

enum class Priority {
    LOW,
    MEDIUM,
    HIGH
}

data class Task(
    @JsonProperty(value = "id")
    var id: Int? = null,
    @JsonProperty(value = "name", required = true)
    val name: String,
    @JsonProperty(value = "priority", required = true)
    var priority: Priority,
    @JsonProperty(value = "completed")
    var completed: Boolean = false) {
    override fun toString(): String = ("$id. [${if (completed) "x" else " "}] $name : ${priority}")
}

abstract class TasksRepository {
    abstract fun getTasks(completed: Boolean = true): List<Task>
    abstract fun addTask(task: Task): Int
    abstract fun deleteTask(id: Int)
    abstract fun completeTask(id: Int)
    abstract fun uncompleteTask(id: Int)
}
