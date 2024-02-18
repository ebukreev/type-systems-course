package dev.bukreev.types

import dev.bukreev.types.parsing.stellaParser.*
import org.antlr.v4.runtime.RuleContext
import kotlin.system.exitProcess

sealed interface Error {
    fun stringify(): String
    
    fun report(): Nothing {
        System.err.println(stringify())
        exitProcess(1)
    }
}

data object ErrorMissingMain : Error {
    override fun stringify(): String {
        return """
            ERROR_MISSING_MAIN:
              в программе отсутствует функция main
        """.trimIndent()
    }
}

data class ErrorUndefinedVariable(val varName: String, val parentExpression: RuleContext) : Error {
    override fun stringify(): String {
        return """
            ERROR_UNDEFINED_VARIABLE:
              в выражении
                ${parentExpression.toStringTree()}
              содержится необъявленная переменная
                $varName
        """.trimIndent()
    }
}

data class ErrorUnexpectedTypeForExpression(val expected: Type, val actual: Type?, val expression: ExprContext) : Error {
    override fun stringify(): String {
       return """
           ERROR_UNEXPECTED_TYPE_FOR_EXPRESSION:
             ожидается тип
               $expected
             но получен тип
               $actual
             для выражения
               ${expression.toStringTree()}
       """.trimIndent()
    }
}

data class ErrorNotAFunction(val expression: ExprContext, val type: Type) : Error {
    override fun stringify(): String {
        return """
           ERROR_NOT_A_FUNCTION:
             для выражения
               ${expression.toStringTree()}
             ожидается функциональный тип
             но получен тип
               $type
       """.trimIndent()
    }
}

data object ErrorNotATuple : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorNotARecord : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorNotAList : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data class ErrorUnexpectedLambda(val expected: Type, val actual: Type?, val expression: ExprContext) : Error {
    override fun stringify(): String {
        return """
           ERROR_UNEXPECTED_LAMBDA:
             ожидается не функциональный тип
               $expected
             но получен функциональный тип
               $actual
             для выражения
               ${expression.toStringTree()}
       """.trimIndent()
    }
}

data class ErrorUnexpectedTypeForParameter(val expected: Type, val actual: Type?, val expression: ExprContext) : Error {
    override fun stringify(): String {
        return """
           ERROR_UNEXPECTED_TYPE_FOR_PARAMETER:
             парметр ожидаемого типа
               $expected
             не соответствует актуальному
               $actual
             для выражения
               ${expression.toStringTree()}
       """.trimIndent()
    }
}

data object ErrorUnexpectedTuple : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorUnexpectedRecord : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorUnexpectedList : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorUnexpectedInjection : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorMissingRecordFields : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorUnexpectedRecordFields : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorUnexpectedFieldAccess : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorTupleIndexOfBounds : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorUnexpectedTupleLength : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorAmbiguousSumType : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorAmbiguousList : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorIllegalEmptyMatching : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorNonexhaustiveMatchPatterns : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorUnexpectedPatternForType : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

fun reportUnexpectedType(expected: Type, actual: Type?, expression: ExprContext): Nothing {
    if (expected !is FuncType && actual is FuncType) {
        ErrorUnexpectedLambda(expected, actual, expression).report()
    }

    if (expected is FuncType && actual is FuncType && !isUnifiable(expected.argType, actual.argType)) {
        ErrorUnexpectedTypeForParameter(expected, actual, expression).report()
    }

    ErrorUnexpectedTypeForExpression(expected, actual, expression).report()
}