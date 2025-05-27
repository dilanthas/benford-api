package org.benford

import kotlinx.coroutines.runBlocking
import java.nio.file.Path
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import kotlin.test.assertFailsWith

class MainTest {

    @TempDir
    lateinit var tempDir: Path

    @Test
    fun `exits with error if no arguments`() {
        val exception = assertFailsWith<TestExitException> {
            runBlocking {
                startApp(emptyArray()) { throw TestExitException(it) }
            }
        }
        assertEquals(1, exception.code)
    }

    @Test
    fun `exits if config file does not exist`() {
        val path = tempDir.resolve("missing.yaml").toString()
        val exception = assertFailsWith<TestExitException> {
            runBlocking {
                startApp(arrayOf(path)) { throw TestExitException(it) }
            }
        }
        assertEquals(1, exception.code)
    }

    class TestExitException(val code: Int) : RuntimeException()
}