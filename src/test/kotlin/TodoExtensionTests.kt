import data.Priority
import data.Task
import data.TasksRepositoryMemory
import io.github.serpro69.kfaker.Faker
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe

class TodoExtensionTests : ShouldSpec({
    extension(RepeatOnFailureExtension())

    val faker = Faker()
    val tasksRepositoryMemory = TasksRepositoryMemory()

    fun createNewTask(): Task {
        val name = faker.random.randomString(10)
        val priority = faker.random.randomValue(Priority.values())
        return Task(name = name, priority = priority)
    }

    should("Add task") {
        val task = createNewTask()
        tasksRepositoryMemory.addTask(task)
        tasksRepositoryMemory.getTasks().stream().map { it.name }.toArray() shouldContain task.name
    }

    should("Complete task") {
        val task = createNewTask()
        val id = tasksRepositoryMemory.addTask(task)
        tasksRepositoryMemory.completeTask(id)
        tasksRepositoryMemory.getTasks(true).first { it.id == id }.completed shouldBe true
    }

    should("Delete task") {
        val task = createNewTask()
        val id = tasksRepositoryMemory.addTask(task)
        tasksRepositoryMemory.deleteTask(id)
        tasksRepositoryMemory.getTasks(true).none { it.id == id } shouldBe true
    }

    should("Repeat failed test") {
        val task = createNewTask()
        tasksRepositoryMemory.getTasks().stream().map { it.name }.toArray() shouldContain task.name
    }
})
