package helpers

import database.DatabaseManager

// Репозиторий для хранения тестовых тасок
class TestDataRepository {
    // С помощью переменной numberOfTasks можно задавать число тасок, которые будут сгенерированы
    private val numberOfTasks = 10
    val testTasks = DatabaseManager().getListOfTasksFromDb(numberOfTasks)
}