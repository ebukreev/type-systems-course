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

data class ErrorNotATuple(val expression: ExprContext, val type: Type) : Error {
    override fun stringify(): String {
        return """
           ERROR_NOT_A_TUPLE:
             для выражения
               ${expression.toStringTree()}
             ожидается тип кортежа
             но получен тип
               $type
       """.trimIndent()
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

data class ErrorUnexpectedTuple(val expected: Type, val actual: Type?, val expression: ExprContext) : Error {
    override fun stringify(): String {
        return """
           ERROR_UNEXPECTED_TUPLE:
             ожидается не тип кортежа
               $expected
             но получен тип кортежа
               $actual
             для выражения
               ${expression.toStringTree()}
       """.trimIndent()
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

data class ErrorTupleIndexOfBounds(val expression: ExprContext, val index: Int) : Error {
    override fun stringify(): String {
        return """
           ERROR_TUPLE_INDEX_OUT_OF_BOUNDS:
             в выражении
               ${expression.toStringTree()}
             осуществляется попытка извлечь отсутствующий компонент кортежа
               ${index + 1}
       """.trimIndent()
    }
}

data class ErrorUnexpectedTupleLength(val expected: TupleType, val actual: TupleType, val expression: ExprContext) : Error {
    override fun stringify(): String {
        return """
           ERROR_UNEXPECTED_TUPLE_LENGTH:
             ожидается кортеж
               $expected
             с длинной ${expected.types.size}
             но получен тип кортежа
               $actual
             с длинной ${actual.types.size}
             для выражения
               ${expression.toStringTree()}
       """.trimIndent()
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

    if (expected !is TupleType && actual is TupleType) {
        ErrorUnexpectedTuple(expected, actual, expression).report()
    }

    if (expected is TupleType && actual is TupleType && expected.types.size != actual.types.size) {
        ErrorUnexpectedTupleLength(expected, actual, expression).report()
    }

    ErrorUnexpectedTypeForExpression(expected, actual, expression).report()
}