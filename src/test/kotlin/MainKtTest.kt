import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class MainKtTest {

    @Test
    fun `renderMenu chosen 3 when System InputStream contains 3`() {
        System.setIn("3".byteInputStream())
        assertEquals(3, renderMenu())
    }

    @Test
    fun `renderMenu chosen 4 when System InputStream contains 4`() {
        System.setIn("4".byteInputStream())
        assertEquals(4, renderMenu())
    }
    @Test
    fun `renderMenu chosen 0 when System InputStream contains A`() {
        System.setIn("A".byteInputStream())
        assertEquals(0, renderMenu())
    }

}