package dev.bukreev.types

import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.readText

class TypeCheckOkTest {
    @Test
    fun testOkData() {
        val projectDirAbsolutePath = Paths.get("").toAbsolutePath().toString()
        val resourcesPath = Paths.get(projectDirAbsolutePath, "/src/test/resources/ok")
        val paths = Files.walk(resourcesPath)
            .filter { item -> Files.isRegularFile(item) }
            .filter { item -> item.toString().endsWith(".st") }

        paths.forEach { Parser.parse(it.readText()).accept(TypeChecker()) }
    }
}