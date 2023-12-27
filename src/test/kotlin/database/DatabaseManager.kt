package database

import data.Priority
import data.Task
import helpers.getRandomString
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseConnection {
    val connect = Database.connect("jdbc:sqlite:test_data.db", "org.sqlite.JDBC")
}

class DatabaseManager {
    // Создает данные для тестовых тасок и записывает их в БД
    private fun generateTestDataAndWrightToBd(numberOfTasks: Int) {
        DatabaseConnection.connect
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(TasksForTest)
            for (i in 0..numberOfTasks) {
                TasksForTest.insert {
                    it[name] = getRandomString((0..100).random())
                    it[priority] = Priority.entries.random().name
                }
            }
        }
    }
    // Очищает БД
    private fun clearDb() {
        TasksForTest.deleteAll()
    }

    // Считывает данные из БД, создает из них таски, возвращает список тасок и очищает базу
    fun getListOfTasksFromDb(numberOfTasks: Int): MutableList<Task> {
        generateTestDataAndWrightToBd(numberOfTasks)
        val tasks = mutableListOf<Task>()
        transaction {
            addLogger(StdOutSqlLogger)
            TasksForTest.selectAll().forEach {
                val name = it[TasksForTest.name]
                val priority = Priority.valueOf(it[TasksForTest.priority])
                tasks.add(Task(name = name, priority = priority))
            }
            clearDb()
        }
        return tasks
    }
}