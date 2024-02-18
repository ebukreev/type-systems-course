package dev.bukreev.types

import java.lang.StringBuilder


fun main() {
    val sb = StringBuilder()
    var line = readlnOrNull()
    while (line != null) {
        sb.append(line)
        line = readlnOrNull()
    }
    Parser.parse(sb.toString()).accept(TypeChecker())
}

fun debug() {
    val exampleStellaCode = """
        language core;
        fn Bool::not(b : Bool) -> Bool {
            return
                if b then false else true
        }
        
        fn twice(f : fn(Bool) -> Bool) -> (fn(Bool) -> Bool) {
            return fn(x : Bool) {
                return f(f(x))
            }
        }
        
        fn main(b : Bool) -> Bool {
            return twice(Bool::not)(b)
        }
    """.trimIndent()

    val tree = Parser.parse(exampleStellaCode)

    println(tree.accept(TypeChecker()))
}