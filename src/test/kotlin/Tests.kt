import org.junit.jupiter.api.Test

class Tests {
    @Test
    //Сделать тест добавления задачи и появления ее в списке
    fun testAddNewTask(){
        val testRep = createNUncompletedTasks(2)
        val someTask = generateTestTask(false)
        testRep.addTask(someTask)
        verifyIfTaskInList(testRep, someTask,false)
    }

    @Test
    ////Завершить задачу и проверить корректность работы фильтра по завершенным задачам
    fun testIfTaskCompletedTest(){
        val testRep = createNUncompletedTasks(3)
        val controlTask = testRep.getTasks(false)[1]
        controlTask.id?.let { testRep.completeTask(it) }
        verifyIfTaskInList(testRep, controlTask, true)
    }
}