package dev.bukreev.types

import dev.bukreev.types.parsing.stellaParser.ExprContext

class SolveFailException(val expected: Type, val actual: Type, val expr: ExprContext) : Exception()
class OccursInfiniteTypeException(val expected: Type, val actual: Type, val expr: ExprContext) : Exception()

object ConstraintSolver {
    tailrec fun solve(constraints: List<Constraint>) {
        if (constraints.isEmpty()) {
            return
        }

        val constraint = constraints.first()
        val tail = constraints.drop(1)

        when {
            constraint.lhv is TypeVariable && constraint.rhv is TypeVariable && constraint.lhv == constraint.rhv -> {
                return solve(tail)
            }
            constraint.lhv is TypeVariable && !constraint.lhv.containsIn(constraint.rhv, constraint.exprContext) -> {
                return solve(tail.map { it.substitute(constraint.lhv, constraint.rhv) })
            }
            constraint.rhv is TypeVariable && !constraint.rhv.containsIn(constraint.lhv, constraint.exprContext) -> {
                return solve(tail.map {it.substitute(constraint.rhv, constraint.lhv) })
            }
            constraint.lhv is FuncType && constraint.rhv is FuncType -> {
                return solve(tail + listOf(
                    Constraint(constraint.lhv.argTypes.first(), constraint.rhv.argTypes.first(), constraint.exprContext),
                    Constraint(constraint.lhv.returnType, constraint.rhv.returnType, constraint.exprContext)
                )
                )
            }
            constraint.lhv is ListType && constraint.rhv is ListType -> {
                return solve(tail +
                        listOf(Constraint(constraint.lhv.contentType, constraint.rhv.contentType, constraint.exprContext)))
            }
            constraint.lhv is SumType && constraint.rhv is SumType -> {
                return solve(tail + listOf(
                    Constraint(constraint.lhv.inl, constraint.rhv.inl, constraint.exprContext),
                    Constraint(constraint.lhv.inr, constraint.rhv.inr, constraint.exprContext)
                ))
            }
            constraint.lhv is TupleType && constraint.rhv is TupleType -> {
                if (constraint.lhv.types.size == 2 && constraint.rhv.types.size == 2) {
                    return solve(tail + listOf(
                        Constraint(constraint.lhv.types[0], constraint.rhv.types[0], constraint.exprContext),
                        Constraint(constraint.lhv.types[1], constraint.rhv.types[1], constraint.exprContext)
                    ))
                }
            }
            constraint.lhv.isApplicable(constraint.rhv) -> {
                return solve(tail)
            }
        }

        throw SolveFailException(constraint.lhv, constraint.rhv, constraint.exprContext)
    }

    private fun TypeVariable.containsIn(type: Type, expr: ExprContext): Boolean {
        val contains = when (type) {
            NatType, BoolType, UnitType -> false
            is FuncType -> containsIn(type.argTypes.first(), expr) || containsIn(type.returnType, expr)
            is TupleType -> containsIn(type.types[0], expr) || containsIn(type.types[1], expr)
            is SumType -> containsIn(type.inl, expr) || containsIn(type.inr, expr)
            is ListType -> containsIn(type.contentType, expr)
            is TypeVariable -> this == type
            else -> false
        }

        if (contains) {
            throw OccursInfiniteTypeException(this, type, expr)
        }
        return false
    }

    private fun Constraint.substitute(typeVar: TypeVariable, type: Type): Constraint {
        fun Type.substitute(): Type {
            return when (this) {
                typeVar -> type
                is FuncType -> FuncType(argTypes.map { it.substitute() }, returnType.substitute())
                is TupleType -> TupleType(types.map { it.substitute() })
                is SumType -> SumType(inl.substitute(), inr.substitute())
                is ListType -> ListType(contentType.substitute())
                else -> this
            }
        }

        return Constraint(lhv.substitute(), rhv.substitute(), exprContext)
    }
}
