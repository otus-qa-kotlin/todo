package menu

import data.Priority
import data.Task
import data.TasksRepositoryMemory
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe


class ActionsKtKotestTest : ShouldSpec({

    should("should add task from menu") {
        println( " Starting test ${this.testCase.name}")
       val taskTestRepository : TasksRepositoryMemory by lazy { TasksRepositoryMemory() }
       taskTestRepository.addTask(Task(name = "new task", priority = Priority.MEDIUM))
       taskTestRepository.getTasks().stream().map { task -> task.name }.toArray() shouldContain  "new task"
    }

    should("check retry extension") {
        println( " Starting test ${this.testCase.name}")
        true shouldBe false
    }
})


