package dev.bukreev.types

class TypesContext {
    private val typesOfVariables = mutableMapOf<String, MutableList<Type>>()
    private val expectedTypes = mutableListOf<Type?>()

    fun getType(variable: String): Type? {
        return typesOfVariables[variable]?.firstOrNull()
    }

    fun getExpectedType(): Type? {
        return expectedTypes.lastOrNull()
    }

    fun <T> runWithExpectedType(type: Type?, action: () -> T): T {
        expectedTypes.add(type)
        try {
            return action()
        } finally {
            expectedTypes.dropLast(1)
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