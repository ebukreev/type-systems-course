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

data class FuncType(val argType: Type, val returnType: Type) : Type {
    override fun toString(): String {
        return "fn($argType) -> $returnType"
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

fun isUnifiable(fst: Type, snd: Type): Boolean {
    return fst == snd
}