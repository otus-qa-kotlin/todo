package database

import org.jetbrains.exposed.dao.id.IntIdTable

// Объект таблицы БД
object TasksForTest: IntIdTable("test_data") {
    val name = varchar("name", 100)
    val priority = varchar("priority", 10)
}