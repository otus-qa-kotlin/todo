package ru.otus.controller

import ru.otus.data.Task
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.otus.service.TaskService
import javax.websocket.server.PathParam

@RestController
@RequestMapping("api/v1/tasks")
class TaskController(private val taskService: TaskService) {

    @PostMapping
    fun addTask(@RequestBody task: Task): Task {
        return taskService.addTask(task)
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable("id") id: Int) {
        taskService.deleteTask(id)
    }

    @PutMapping("/status/{id}")
    fun completeTask(@PathVariable("id") id: Int, @RequestParam("done") status: Boolean) {
        if (status) {
            return taskService.completeTask(id)
        }
        return taskService.uncompleteTask(id)

    }

    @GetMapping
    fun getTasks(@RequestParam("done") done: Boolean): List<Task> {
        return taskService.getTasks(done)
    }
}