import data.Priority
import data.Task
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class TaskTest {

    @Test
    fun `Uncompleted task toString method should be correct`() {
        val task = Task(89, "SomeTask", Priority.LOW).toString()
        assertEquals("89. [ ] SomeTask : LOW", task)
    }

    @Test
    fun `Completed task toString method should be correct`() {
        val task = Task(12, "SomeTask", Priority.LOW, true).toString()
        assertEquals("12. [x] SomeTask : LOW", task)
    }
}