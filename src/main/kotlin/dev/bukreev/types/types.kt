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

fun isUnifiable(fst: Type, snd: Type): Boolean {
    return fst == snd
}