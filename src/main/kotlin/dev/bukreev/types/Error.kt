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

fun reportUnexpectedType(expectedType: Type, actualType: Type, expression: ExprContext, parser: stellaParser): Nothing {
    if (ExtensionsContext.hasStructuralSubtyping()) {
        ErrorUnexpectedSubtype(expectedType, actualType, expression).report(parser)
    }
    ErrorUnexpectedTypeForExpression(expectedType, actualType, expression).report(parser)
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

data class ErrorUnexpectedLambda(val expected: Type?, val expression: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_UNEXPECTED_LAMBDA:
             ожидается не функциональный тип
               $expected
             но получен функциональный тип
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

data class ErrorUnexpectedTuple(val expected: Type?, val expression: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_UNEXPECTED_TUPLE:
             ожидается не тип кортежа
               $expected
             но получен тип кортежа
             для выражения
               ${expression.toStringTree(parser)}
       """.trimIndent()
    }
}

data class ErrorUnexpectedRecord(val expected: Type?, val expression: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_UNEXPECTED_RECORD:
             ожидается не тип записи
               $expected
             для выражения
               ${expression.toStringTree(parser)}
       """.trimIndent()
    }
}

data class ErrorUnexpectedList(val expected: Type?, val expression: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_UNEXPECTED_LIST:
             ожидается не тип списка
               $expected
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

data class ErrorUnexpectedVariant(val expectedType: Type, val expression: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_UNEXPECTED_VARIANT:
             получена вариант 
               ${expression.toStringTree(parser)}
             но ожидается не вариантный тип
               $expectedType
       """.trimIndent()
    }
}

data class ErrorMissingRecordFields(val expected: Type?, val actual: RecordType, val expression: ExprContext,
                                    val fields: Set<String>) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_MISSING_RECORD_FIELDS:
             ожидается тип
               $expected
             но получен тип записи
               $actual
             в котором нет полей ${fields.joinToString(separator = ", ") }
             для выражения
               ${expression.toStringTree(parser)}
       """.trimIndent()
    }
}

data class ErrorUnexpectedRecordFields(val expected: Type?, val actual: RecordType, val expression: ExprContext,
                                        val fields: Set<String>) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_UNEXPECTED_RECORD_FIELDS:
             ожидается тип
               $expected
             но получен тип записи
               $actual
             в котором есть лишние поля ${fields.joinToString(separator = ", ")}
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

data class ErrorUnexpectedVariantLabel(val label: String, val type: VariantType, val expression: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_UNEXPECTED_VARIANT_LABEL:
             неожиданная метка 
               $label
             для типа варианта
               $type
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

data class ErrorUnexpectedTupleLength(val expected: TupleType, val expression: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_UNEXPECTED_TUPLE_LENGTH:
             ожидается кортеж
               $expected
             с длинной ${expected.types.size}
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

data class ErrorAmbiguousVariantType(val expression: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_AMBIGUOUS_VARIANT_TYPE:
             вариантный тип
               ${expression.toStringTree(parser)}
             невозможно определить 
             в данном контексте отсутсвует ожидаемый вариантный тип
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

data class ErrorAmbiguousPatternType(val pattern: PatternContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_AMBIGUOUS_PATTERN_TYPE:
             невозможно определить тип для паттерна
             ${pattern.toStringTree(parser)}
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

data class ErrorNonexhaustiveLetPatterns(val expectedType: Type, val expression: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_NONEXHAUSTIVE_LET_PATTERNS:
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

data class ErrorIncorrectArityOfMain(val n: Int) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_INCORRECT_ARITY_OF_MAIN:
             функция main объявлена с $n паарметрами
             а должна быть с 1
       """.trimIndent()
    }
}

data class ErrorIncorrectNumberOfArguments(val actual: Int, val expected: Int, val expression: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_INCORRECT_NUMBER_OF_ARGUMENTS:
             вызов
                ${expression.toStringTree(parser)}
             происходит с $actual аргументами, а должен с $expected
       """.trimIndent()
    }
}

data class ErrorUnexpectedNumberOfParametersInLambda(val expected: Int, val expression: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_UNEXPECTED_NUMBER_OF_PARAMETERS_IN_LAMBDA:
             количество параметров анонимной функции
                ${expression.toStringTree(parser)}
             не совпадает с ожидаемым количеством параметров $expected
       """.trimIndent()
    }
}

data class ErrorDuplicatePatternVariable(val pattern: PatternContext, val variable: String) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_DUPLICATE_PATTERN_VARIABLE:
             переменная $variable
             встречается больше 1 раза в паттерне
                ${pattern.toStringTree(parser)}
       """.trimIndent()
    }
}

data class ErrorUnexpectedDataForNullaryLabel(val expr: ExprContext, val type: Type) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_UNEXPECTED_DATA_FOR_NULLARY_LABEL:
             выражение
                ${expr.toStringTree(parser)}
             содержит даннные для метки, хотя ожидается тег без данных
                $type
       """.trimIndent()
    }
}

data class ErrorMissingDataForLabel(val expr: ExprContext, val type: Type) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_MISSING_DATA_FOR_LABEL:
             выражение
                ${expr.toStringTree(parser)}
             не содержит даннные для метки, хотя ожидается тег с данными
                $type
       """.trimIndent()
    }
}

data class ErrorUnexpectedNonNullaryVariantPattern(val expr: PatternContext, val type: Type) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_UNEXPECTED_NON_NULLARY_VARIANT_PATTERN:
             паттерн
                ${expr.toStringTree(parser)}
             содержит тег с данными, хотя ожидается тег без данными
                $type
       """.trimIndent()
    }
}

data class ErrorUnexpectedNullaryVariantPattern(val expr: PatternContext, val type: Type) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_UNEXPECTED_NULLARY_VARIANT_PATTERN:
             паттерн
                ${expr.toStringTree(parser)}
             содержит тег без данных, хотя ожидается тег с данными
                $type
       """.trimIndent()
    }
}

data class ErrorAmbiguousReferenceType(val expr: ConstMemoryContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_AMBIGUOUS_REFERENCE_TYPE:
             неоднозначный тип адреса памяти
                ${expr.toStringTree(parser)}
       """.trimIndent()
    }
}

data class ErrorNotAReference(val expr: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_NOT_A_REFERENCE:
            попытка разыменовать или присвоить значение
            выражению не ссылочного типа
                ${expr.toStringTree(parser)}
       """.trimIndent()
    }
}

data class ErrorUnexpectedMemoryAddress(val expr: ExprContext, val type: Type) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_UNEXPECTED_MEMORY_ADDRESS:
             адрес памяти
                ${expr.toStringTree(parser)}
             используется там, где ожидается тип, отличный от типа-ссылки
                $type
       """.trimIndent()
    }
}

data class ErrorAmbiguousPanicType(val expr: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_AMBIGUOUS_PANIC_TYPE:
             неоднозначный тип ошибки
                ${expr.toStringTree(parser)}
       """.trimIndent()
    }
}

data class ErrorExceptionTypeNotDeclared(val expr: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_EXCEPTION_TYPE_NOT_DECLARED:
              в программе используются исключения, 
              но не объявлен их тип
                ${expr.toStringTree(parser)}
       """.trimIndent()
    }
}

data class ErrorAmbiguousThrowType(val expr: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_AMBIGUOUS_THROW_TYPE:
             неоднозначный тип throw выражения
                ${expr.toStringTree(parser)}
       """.trimIndent()
    }
}

data class ErrorUnexpectedSubtype(val expected: Type, val actual: Type?, val expression: ExprContext) : Error {
    override fun stringify(parser: stellaParser): String {
        return """
           ERROR_UNEXPECTED_SUBTYPE:
             ожидается подтип типа
               $expected
             но получен тип
               $actual
             для выражения
               ${expression.toStringTree(parser)}
       """.trimIndent()
    }
}