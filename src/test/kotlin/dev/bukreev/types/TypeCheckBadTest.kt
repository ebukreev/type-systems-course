package dev.bukreev.types

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.PrintStream
import java.nio.file.Files
import java.nio.file.Paths
import java.security.Permission
import kotlin.io.path.name
import kotlin.io.path.readLines
import kotlin.io.path.readText
import kotlin.test.assertContains
import kotlin.test.assertEquals

class TypeCheckBadTest {
    private var buffer = StringBuilder()
    private val printStream = object : PrintStream(System.err) {
        override fun print(s: String?) {
            buffer.append(s)
        }
    }

    @BeforeEach
    fun setUp() {
        System.setErr(printStream)
        System.setSecurityManager(NoExitSecurityManager())

    }

    @AfterEach
    protected fun tearDown() {
        System.setSecurityManager(null)
    }

    @Test
    fun testBadData() {
        val projectDirAbsolutePath = Paths.get("").toAbsolutePath().toString()
        val resourcesPath = Paths.get(projectDirAbsolutePath, "/src/test/resources/bad")
        val paths = Files.walk(resourcesPath)
            .filter { item -> Files.isRegularFile(item) }
            .filter { item -> item.toString().endsWith(".st") }

        paths.forEach {
            try {
                ExtensionsContext.reset()
                buffer = StringBuilder()
                Parser.parse(it.readText()).let { p -> p.program().accept(TypeChecker(p)) }
                assert(false) { it.toString() }
            } catch (e: ExitException) {
                assertEquals(e.status, 1)
                assertContains(buffer.toString(), it.parent.name, message = it.toString())
            }
        }
    }
}


class ExitException(val status: Int) : SecurityException("There is no escape!")

private class NoExitSecurityManager : SecurityManager() {
    override fun checkPermission(perm: Permission?) {
        // allow anything.
    }

    override fun checkPermission(perm: Permission?, context: Any?) {
        // allow anything.
    }

    override fun checkExit(status: Int) {
        super.checkExit(status)
        throw ExitException(status)
    }
}