package dev.bukreev.types

import dev.bukreev.types.parsing.stellaParser
import dev.bukreev.types.parsing.stellaParser.*
import org.antlr.v4.runtime.RuleContext
import kotlin.system.exitProcess

sealed interface Error {
    fun stringify(parser: stellaParser): String
    
    fun report(parser: stellaParser): Nothing {
        System.err.println(stringify(parser))
        exitProcess(1)
    }
}

data object ErrorMissingMain : Error {
    override fun stringify(parser: stellaParser): String {
        return """
            ERROR_MISSING_MAIN:
              в программе отсутствует функция main
        """.trimIndent()
    }
}

data class ErrorUndefinedVariable(val varName: String, val parentExpression: RuleContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
            ERROR_UNDEFINED_VARIABLE:
              в выражении
                ${parentExpression.toStringTree(parser)}
              содержится необъявленная переменная
                $varName
        """.trimIndent()
    }
}

data class ErrorUnexpectedTypeForExpression(val expected: Type, val actual: Type?, val expression: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
       return """
           ERROR_UNEXPECTED_TYPE_FOR_EXPRESSION:
             ожидается тип
               $expected
             но получен тип
               $actual
             для выражения
               ${expression.toStringTree(parser)}
       """.trimIndent()
    }
}

data class ErrorNotAFunction(val expression: ExprContext, val type: Type) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_NOT_A_FUNCTION:
             для выражения
               ${expression.toStringTree(parser)}
             ожидается функциональный тип
             но получен тип
               $type
       """.trimIndent()
    }
}

data class ErrorNotATuple(val expression: ExprContext, val type: Type) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_NOT_A_TUPLE:
             для выражения
               ${expression.toStringTree(parser)}
             ожидается тип кортежа
             но получен тип
               $type
       """.trimIndent()
    }
}

data class ErrorNotARecord(val expression: ExprContext, val type: Type) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_NOT_A_RECORD:
             для выражения
               ${expression.toStringTree(parser)}
             ожидается тип записи
             но получен тип
               $type
       """.trimIndent()
    }
}

data class ErrorNotAList(val expression: ExprContext, val type: Type) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_NOT_A_LIST:
             для выражения
               ${expression.toStringTree(parser)}
             ожидается тип списка
             но получен тип
               $type
       """.trimIndent()
    }
}

data class ErrorUnexpectedLambda(val expected: Type, val actual: Type?, val expression: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_UNEXPECTED_LAMBDA:
             ожидается не функциональный тип
               $expected
             но получен функциональный тип
               $actual
             для выражения
               ${expression.toStringTree(parser)}
       """.trimIndent()
    }
}

data class ErrorUnexpectedTypeForParameter(val expected: Type, val actual: Type?, val expression: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_UNEXPECTED_TYPE_FOR_PARAMETER:
             парметр ожидаемого типа
               $expected
             не соответствует актуальному
               $actual
             для выражения
               ${expression.toStringTree(parser)}
       """.trimIndent()
    }
}

data class ErrorUnexpectedTuple(val expected: Type, val actual: Type?, val expression: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_UNEXPECTED_TUPLE:
             ожидается не тип кортежа
               $expected
             но получен тип кортежа
               $actual
             для выражения
               ${expression.toStringTree(parser)}
       """.trimIndent()
    }
}

data class ErrorUnexpectedRecord(val expected: Type, val actual: Type?, val expression: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_UNEXPECTED_RECORD:
             ожидается не тип записи
               $expected
             но получен тип записи
               $actual
             для выражения
               ${expression.toStringTree(parser)}
       """.trimIndent()
    }
}

data class ErrorUnexpectedList(val expected: Type, val actual: Type?, val expression: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_UNEXPECTED_LIST:
             ожидается не тип списка
               $expected
             но получен тип списка
               $actual
             для выражения
               ${expression.toStringTree(parser)}
       """.trimIndent()
    }
}

data class ErrorUnexpectedInjection(val expectedType: Type, val expression: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_UNEXPECTED_INJECTION:
             получена инъекция 
               ${expression.toStringTree(parser)}
             но ожидается не тип-сумма
               $expectedType
       """.trimIndent()
    }
}

data class ErrorMissingRecordFields(val expected: RecordType, val actual: RecordType, val expression: ExprContext,
                                    val fields: Set<Pair<String, Type>>) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_MISSING_RECORD_FIELDS:
             ожидается тип записи
               $expected
             но получен тип записи
               $actual
             в котором нет полей ${fields.joinToString(separator = ", ") { "${it.first} : ${it.second}" }}
             для выражения
               ${expression.toStringTree(parser)}
       """.trimIndent()
    }
}

data class ErrorUnexpectedRecordFields(val expected: RecordType, val actual: RecordType, val expression: ExprContext,
                                        val fields: Set<Pair<String, Type>>) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_UNEXPECTED_RECORD_FIELDS:
             ожидается тип записи
               $expected
             но получен тип записи
               $actual
             в котором есть лишние поля ${fields.joinToString(separator = ", ") { "${it.first} : ${it.second}" }}
             для выражения
               ${expression.toStringTree(parser)}
       """.trimIndent()
    }
}

data class ErrorUnexpectedFieldAccess(val recordType: RecordType, val expression: ExprContext, val label: String) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_UNEXPECTED_FIELD_ACCESS:
             попытка извлечь отсутствующее поле записи
               $label
             для типа записи
               $recordType
             в выражении
               ${expression.toStringTree(parser)}
       """.trimIndent()
    }
}

data class ErrorTupleIndexOfBounds(val expression: ExprContext, val index: Int) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_TUPLE_INDEX_OUT_OF_BOUNDS:
             в выражении
               ${expression.toStringTree(parser)}
             осуществляется попытка извлечь отсутствующий компонент кортежа
               ${index + 1}
       """.trimIndent()
    }
}

data class ErrorUnexpectedTupleLength(val expected: TupleType, val actual: TupleType, val expression: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_UNEXPECTED_TUPLE_LENGTH:
             ожидается кортеж
               $expected
             с длинной ${expected.types.size}
             но получен тип кортежа
               $actual
             с длинной ${actual.types.size}
             для выражения
               ${expression.toStringTree(parser)}
       """.trimIndent()
    }
}

data class ErrorAmbiguousSumType(val expression: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_AMBIGUOUS_SUM_TYPE:
             тип инъекции
               ${expression.toStringTree(parser)}
             невозможно определить 
             в данном контексте отсутсвует ожидаемый тип-сумма
       """.trimIndent()
    }
}

data class ErrorAmbiguousList(val expression: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_AMBIGUOUS_LIST:
             тип списка
               ${expression.toStringTree(parser)}
             невозможно определить 
             в данном контексте отсутсвует ожидаемый тип списка
       """.trimIndent()
    }
}

data class ErrorIllegalEmptyMatching(val expression: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_ILLEGAL_EMPTY_MATCHING:
             match выражение
                ${expression.toStringTree(parser)}
             с пустым списком альтернатив
       """.trimIndent()
    }
}

data class ErrorNonexhaustiveMatchPatterns(val expectedType: Type, val expression: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_NONEXHAUSTIVE_MATCH_PATTERNS:
             не все образцы для типа
                $expectedType
             перечислены в выражении
                ${expression.toStringTree(parser)}
       """.trimIndent()
    }
}

data class ErrorUnexpectedPatternForType(val type: Type?, val pattern: PatternContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_UNEXPECTED_PATTERN_FOR_TYPE:
             образец
                ${pattern.toStringTree(parser)}
             не соответствует типу разбираемого выражения
                $type
       """.trimIndent()
    }
}

fun reportUnexpectedType(expected: Type, actual: Type?, expression: ExprContext, parser: stellaParser): Nothing {
    if (expected !is FuncType && actual is FuncType) {
        ErrorUnexpectedLambda(expected, actual, expression).report(parser)
    }

    if (expected is FuncType && actual is FuncType && !isUnifiable(expected.argType, actual.argType)) {
        ErrorUnexpectedTypeForParameter(expected, actual, expression).report(parser)
    }

    if (expected !is TupleType && actual is TupleType) {
        ErrorUnexpectedTuple(expected, actual, expression).report(parser)
    }

    if (expected is TupleType && actual is TupleType && expected.types.size != actual.types.size) {
        ErrorUnexpectedTupleLength(expected, actual, expression).report(parser)
    }

    if (expected !is RecordType && actual is RecordType) {
        ErrorUnexpectedRecord(expected, actual, expression).report(parser)
    }

    if (expected is RecordType && actual is RecordType) {
        val unexpectedFields = actual.fields.subtract(expected.fields.toSet())
        if (unexpectedFields.isNotEmpty()) {
            ErrorUnexpectedRecordFields(expected, actual, expression, unexpectedFields).report(parser)
        }

        val missingFields = expected.fields.subtract(actual.fields.toSet())
        if (missingFields.isNotEmpty()) {
            ErrorMissingRecordFields(expected, actual, expression, missingFields).report(parser)
        }
    }

    if (expected !is ListType && actual is ListType) {
        ErrorUnexpectedList(expected, actual, expression).report(parser)
    }

    ErrorUnexpectedTypeForExpression(expected, actual, expression).report(parser)
}