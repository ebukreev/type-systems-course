package dev.bukreev.types

import dev.bukreev.types.parsing.stellaLexer
import dev.bukreev.types.parsing.stellaParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

object Parser {
    fun parse(text: String): stellaParser {
        val lexer = stellaLexer(CharStreams.fromString(text))
        val tokens = CommonTokenStream(lexer)

        return stellaParser(tokens)
    }
}