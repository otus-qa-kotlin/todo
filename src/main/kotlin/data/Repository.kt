package data

class TasksRepositoryMemory : TasksRepository() {

    val tasks = mutableListOf<Task>()

    fun nextId(): Int = tasks.maxByOrNull { it.id ?: 0 }?.id?.inc() ?: 1
    override fun getTasks(completed: Boolean?): List<Task> {
        return tasks
            .toList()
            .filter { completed == null || it.completed == completed }
    }

    override fun addTask(task: Task): Int {
        val id = nextId()
        tasks.add(task.copy(id = id))
        return id
    }

    override fun deleteTask(id: Int) {
        tasks.removeAll { it.id == id }
    }

    override fun completeTask(id: Int) {
        tasks.first { it.id == id }.completed = true
    }

    override fun uncompleteTask(id: Int) {
        tasks.first { it.id == id }.completed = false
    }
}
