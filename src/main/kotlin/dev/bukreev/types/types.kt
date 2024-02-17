package dev.bukreev.types

sealed interface Type

sealed class IntType : Type

sealed class BoolType : Type

sealed class FuncType(val argType: Type, val returnType: Type) : Type