package dev.bukreev.types

class TypesContext {
    private val typesOfVariables = mutableMapOf<String, MutableList<Type>>()

    fun getType(variable: String): Type? {
        return typesOfVariables[variable]?.firstOrNull()
    }

    fun <T> runWithTypeInfo(variable: String, type: Type, action: () -> T): T {
        addTypeInfo(variable, type)
        try {
            return action()
        } finally {
            removeTypeInfo(variable)
        }
    }

    private fun addTypeInfo(variable: String, type: Type) {
        (typesOfVariables.getOrPut(variable) { mutableListOf() }).add(type)
    }

    private fun removeTypeInfo(variable: String) {
        val typesInfo = typesOfVariables[variable]!!.apply { removeLast() }

        if (typesInfo.isEmpty()) {
            typesOfVariables.remove(variable)
        }
    }
}