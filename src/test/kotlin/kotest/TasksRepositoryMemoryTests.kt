package kotest

import data.Priority
import data.Task
import data.TasksRepositoryMemory
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class TasksRepositoryMemoryTests : BehaviorSpec({

    given("empty repository and uncompleted task") {
        val taskTestRepository = TasksRepositoryMemory()
        val task = Task(1, "New Task", Priority.HIGH, false)
        `when`("add new task to repository") {
            taskTestRepository.addTask(task)
            then("added task should be present in repository") {
                taskTestRepository.getTasks().size shouldBe 1
                taskTestRepository.getTasks().first() shouldBe task
            }
        }
    }

    given("repository with one completed task") {
        val task = Task(1, "Completed Task", Priority.HIGH, true)
        val taskTestRepository = TasksRepositoryMemory()
        taskTestRepository.addTask(task)
        `when`("User execute delete task action") {
            taskTestRepository.deleteTask(1)
            then("Completed task should be removed from repository") {
                taskTestRepository.getTasks().size shouldBe 0
            }
        }
    }

    given("Task repository with not completed task") {
        val task = Task(1, "Task to complete", Priority.HIGH, false)
        val taskTestRepository = TasksRepositoryMemory()
        taskTestRepository.addTask(task)
        `when`("User completes the task") {
            taskTestRepository.completeTask(1)
            then("Task should be marked as completed") {
                taskTestRepository.getTasks().size shouldBe 1
                taskTestRepository.getTasks().first().completed shouldBe true
            }
        }
    }

    given("Task repository with completed task") {
        val task = Task(1, "Task to uncomplete", Priority.HIGH, true)
        val taskTestRepository = TasksRepositoryMemory()
        taskTestRepository.addTask(task)
        `when`("User uncompletes the task") {
            taskTestRepository.uncompleteTask(1)
            then("Task should be marked as uncompleted") {
                taskTestRepository.getTasks().size shouldBe 1
                taskTestRepository.getTasks().first().completed shouldBe false
            }
        }
    }

    given("Tesk repository with two tasks: 1-completed, 2-uncompleted") {
        val taskCompleted = Task(1, "Task completed", Priority.LOW, true)
        val taskUncompleted = Task(2, "Task uncompleted", Priority.HIGH, false)
        val taskTestRepository = TasksRepositoryMemory()
        taskTestRepository.addTask(taskCompleted)
        taskTestRepository.addTask(taskUncompleted)
        `when`("User filters completed tasks") {
            val filteredTasks = taskTestRepository.getTasks(false)
            then("Completed task should be filtered out") {
                filteredTasks.size shouldBe 1
                filteredTasks.first() shouldBe taskUncompleted
            }
        }
    }
})
