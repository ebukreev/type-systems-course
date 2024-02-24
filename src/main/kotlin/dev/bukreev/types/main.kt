package dev.bukreev.types

import java.lang.StringBuilder


fun main() {
    val sb = StringBuilder()
    var line = readlnOrNull()
    while (line != null) {
        sb.append(line)
        line = readlnOrNull()
    }

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