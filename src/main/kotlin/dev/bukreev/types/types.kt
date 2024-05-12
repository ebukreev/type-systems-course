package dev.bukreev.types

sealed interface Type

data object NatType : Type {
    override fun toString(): String {
        return "Nat"
    }
}

data object BoolType : Type {
    override fun toString(): String {
        return "Bool"
    }
}

data class FuncType(val argTypes: List<Type>, val returnType: Type) : Type {
    override fun toString(): String {
        return "fn(${argTypes.joinToString(separator = ", ")}) -> $returnType"
    }
}

data object UnitType : Type {
    override fun toString(): String {
        return "Unit"
    }
}

data class TupleType(val types: List<Type>) : Type {
    override fun toString(): String {
        return "{${types.joinToString(separator = ", ")}}"
    }
}

data class RecordType(val fields: List<Pair<String, Type>>) : Type {
    override fun toString(): String {
        return "{${fields.joinToString(separator = ", ") { "${it.first} : ${it.second}" }}}"
    }
}

data class SumType(val inl: Type, val inr: Type) : Type {
    override fun toString(): String {
        return "$inl + $inr"
    }
}

data class ListType(val contentType: Type) : Type {
    override fun toString(): String {
        return "[$contentType]"
    }
}

data class VariantType(val variants: List<Pair<String, Type?>>) : Type {
    override fun toString(): String {
        return "<| ${variants.joinToString(separator = ", ") { 
            "${it.first}${if (it.second != null)" : ${it.second}" else ""}" 
        }} |>"
    }
}

data class RefType(val nestedType: Type) : Type {
    override fun toString(): String {
        return "&$nestedType"
    }
}

data class TypeVariable(val num: Int) : Type {
    override fun toString(): String {
        return "?T$num"
    }
}

class UniversalTypeVar(val name: String) : Type {
    override fun toString(): String {
        return name
    }
}

data class UniversalType(
    val variables: List<UniversalTypeVar>,
    val nestedType: Type
) : Type {
    override fun toString(): String {
        return variables.joinToString { "forall $it. " } + nestedType.toString()
    }
}


data object Top : Type

data object Bot : Type

fun Type.isApplicable(expected: Type): Boolean {
    if (this is UniversalTypeVar && expected is UniversalTypeVar)
        return this.name == expected.name
    if (this is UniversalType && expected is UniversalType) {
        if (this.variables.size != expected.variables.size) {
            return false
        }
        return this.substitute(this.variables
            .mapIndexed { index, typeVar -> Pair(typeVar, expected.variables[index]) }.toMap())
            .isApplicable(expected.nestedType)
    }

    if (this == expected) return true

    if (!ExtensionsContext.hasStructuralSubtyping()) return false

    if (this == Bot) return true
    if (expected == Top) return true

    if (this is FuncType && expected is FuncType) {
        if (!returnType.isApplicable(expected.returnType)) return false
        if (argTypes.size != expected.argTypes.size) return false

        return argTypes.withIndex().all { (index, value) -> expected.argTypes[index].isApplicable(value) }
    }

    if (this is RecordType && expected is RecordType) {
        val thisFields = fields.toMap()
        val expectedFields = expected.fields.toMap()

        if ((expectedFields - thisFields.keys).isNotEmpty()) {
            return false
        }

        return (thisFields.filter { expectedFields.containsKey(it.key) })
            .all { (name, type) -> type.isApplicable(expectedFields[name]!!) }
    }

    if (this is TupleType && expected is TupleType) {
        if (types.size != expected.types.size) return false

        return types.withIndex().all { (index, type) -> type.isApplicable(expected.types[index]) }
    }

    if (this is SumType && expected is SumType) {
        return inl.isApplicable(expected.inl) && inr.isApplicable(expected.inr)
    }

    if (this is VariantType && expected is VariantType) {
        val thisVariants = variants.toMap()
        val expectedVariants = expected.variants.toMap()

        if ((thisVariants - expectedVariants.keys).isNotEmpty() ||
            !thisVariants.keys.all { expectedVariants.containsKey(it) }) {
            return false
        }

        return thisVariants.all { (name, type) -> type?.isApplicable(expectedVariants[name]!!) ?: true }
    }

    if (this is ListType && expected is ListType) {
        return contentType.isApplicable(expected.contentType)
    }

    if (this is RefType && expected is RefType) {
        return nestedType.isApplicable(expected.nestedType) && expected.nestedType.isApplicable(nestedType)
    }

    return false
}

fun Type.substitute(typesMapping: Map<UniversalTypeVar, Type>): Type {
    return when (this) {
        is UniversalType -> {
            val substituted = nestedType.substitute(typesMapping)
            if (!typesMapping.keys.all { this.variables.contains(it) }) {
                UniversalType(this.variables, substituted)
            } else {
                substituted
            }
        }
        is FuncType -> FuncType(argTypes.map { it.substitute(typesMapping) }, returnType.substitute(typesMapping))
        is ListType -> ListType(contentType.substitute(typesMapping))
        is RecordType -> RecordType(fields.map { Pair(it.first, it.second.substitute(typesMapping)) })
        is SumType -> SumType(inl.substitute(typesMapping), inr.substitute(typesMapping))
        is TupleType -> TupleType(types.map { it.substitute(typesMapping) })
        is VariantType -> VariantType(variants.map { Pair(it.first, it.second?.substitute(typesMapping)) })

        is UniversalTypeVar -> typesMapping.getOrDefault(this, this)

        else -> this
    }
}