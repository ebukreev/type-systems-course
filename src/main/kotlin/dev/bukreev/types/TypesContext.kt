package dev.bukreev.types

import dev.bukreev.types.parsing.stellaParser.ExprContext

data class Constraint(
    val lhv: Type,
    val rhv: Type,
    val exprContext: ExprContext
)

class TypesContext {
    private val typesOfVariables = mutableMapOf<String, MutableList<Type>>()
    private val expectedTypes = mutableListOf<Type?>()

    val constraintSet = mutableListOf<Constraint>()

    var typeVariablesNum = 0

    fun getType(variable: String): Type? {
        return typesOfVariables[variable]?.lastOrNull()
    }

    fun getExpectedType(): Type? {
        return expectedTypes.lastOrNull()
    }

    fun <T> runWithExpectedType(type: Type?, action: () -> T): T {
        expectedTypes.add(type)
        try {
            return action()
        } finally {
            expectedTypes.removeLast()
        }
    }

    fun <T> runWithTypeInfo(variable: String, type: Type, action: () -> T): T {
        addTypeInfo(variable, type)
        try {
            return action()
        } finally {
            removeTypeInfo(variable)
        }
    }

    fun <T> runWithTypesInfo(typesInfo : List<Pair<String, Type>>, action: () -> T): T {
        typesInfo.forEach { addTypeInfo(it.first, it.second) }
        try {
            return action()
        } finally {
            typesInfo.forEach { removeTypeInfo(it.first) }
        }
    }

    fun addTypeInfo(variable: String, type: Type) {
        (typesOfVariables.getOrPut(variable) { mutableListOf() }).add(type)
    }

    private fun removeTypeInfo(variable: String) {
        val typesInfo = typesOfVariables[variable]!!.apply { removeLast() }

        if (typesInfo.isEmpty()) {
            typesOfVariables.remove(variable)
        }
    }
}