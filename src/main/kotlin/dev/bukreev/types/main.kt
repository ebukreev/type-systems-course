package dev.bukreev.types

import java.lang.StringBuilder
import java.util.*

fun main() {
    val sb = StringBuilder()
    val scanner = Scanner(System.`in`)
    scanner.useDelimiter(System.lineSeparator())
    while (scanner.hasNext()) {
        sb.appendLine(scanner.next())
    }
    scanner.close()

    val parser = Parser.parse(sb.toString())
    parser.program().accept(TypeChecker(parser))
}

fun debug() {
    val exampleStellaCode = """
        language core;
        extend with #lists;
        
        fn nonempty(list : [Nat]) -> Bool {
          return if List::isempty(list) then false else true
        }
        
        fn first_or_default(list : [Nat]) -> Nat {
          return if nonempty(list) then List::head(list) else 0
        }
        
        fn main(default : Nat) -> Nat {
          return first_or_default([])
        }
    """.trimIndent()

    val parser = Parser.parse(exampleStellaCode)

    println(parser.program().accept(TypeChecker(parser)))
}