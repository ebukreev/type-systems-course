package dev.bukreev.types

sealed interface Type

data object NatType : Type

data object BoolType : Type

data class FuncType(val argType: Type, val returnType: Type) : Type
