package ru.otus

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class TodoApp


fun main() {
    runApplication<TodoApp>()
}