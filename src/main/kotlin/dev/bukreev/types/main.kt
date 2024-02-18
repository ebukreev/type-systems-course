package dev.bukreev.types

import dev.bukreev.types.parsing.stellaLexer
import dev.bukreev.types.parsing.stellaParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream


fun main() {
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

    val lexer = stellaLexer(CharStreams.fromString(exampleStellaCode))
    val tokens = CommonTokenStream(lexer)
    val parser = stellaParser(tokens)
    val tree = parser.program()

    println(tree.accept(TypeChecker()))
}