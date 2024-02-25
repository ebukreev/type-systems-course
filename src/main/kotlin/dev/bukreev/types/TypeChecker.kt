package dev.bukreev.types

import dev.bukreev.types.parsing.stellaParser
import dev.bukreev.types.parsing.stellaParser.*
import dev.bukreev.types.parsing.stellaParserVisitor
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode
import org.antlr.v4.runtime.tree.TerminalNode

class TypeChecker(private val parser: stellaParser,
                  private val typesContext: TypesContext = TypesContext()) : stellaParserVisitor<Type> {
    override fun visit(tree: ParseTree): Type {
        TODO("Not yet implemented")
    }

    override fun visitChildren(node: RuleNode): Type {
        TODO("Not yet implemented")
    }

    override fun visitTerminal(node: TerminalNode): Type {
        TODO("Not yet implemented")
    }

    override fun visitErrorNode(node: ErrorNode): Type {
        TODO("Not yet implemented")
    }

    override fun visitStart_Program(ctx: Start_ProgramContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitStart_Expr(ctx: Start_ExprContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitStart_Type(ctx: Start_TypeContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitProgram(ctx: ProgramContext): Type {
        var mainFunctionType: Type? = null
        for (decl in ctx.decls) {
            val type = decl.accept(this)
            if (decl is DeclFunContext && decl.name.text == "main") {
                mainFunctionType = type
            }
        }

        return mainFunctionType ?: ErrorMissingMain.report(parser)
    }

    override fun visitLanguageCore(ctx: LanguageCoreContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitAnExtension(ctx: AnExtensionContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitDeclFun(ctx: DeclFunContext): Type {
        val param = ctx.paramDecl
        val paramType = param.paramType.accept(this)
        val returnType = ctx.returnType.accept(this)
        val funcType = FuncType(paramType, returnType)

        typesContext.addTypeInfo(ctx.name.text, funcType)
        return typesContext.runWithTypeInfo<Type>(param.name.text, paramType) {
            typesContext.runWithExpectedType(returnType) {
                val returnExpressionType = ctx.returnExpr.accept(this)
                if (!isUnifiable(returnType, returnExpressionType)) {
                    ErrorUnexpectedTypeForExpression(returnType, returnExpressionType, ctx.returnExpr).report(parser)
                }

                funcType
            }
        }
    }

    override fun visitDeclFunGeneric(ctx: DeclFunGenericContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitDeclTypeAlias(ctx: DeclTypeAliasContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitDeclExceptionType(ctx: DeclExceptionTypeContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitDeclExceptionVariant(ctx: DeclExceptionVariantContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitInlineAnnotation(ctx: InlineAnnotationContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitParamDecl(ctx: ParamDeclContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitFold(ctx: FoldContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitAdd(ctx: AddContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitIsZero(ctx: IsZeroContext): Type {
        val argType = typesContext.runWithExpectedType(NatType) { ctx.n.accept(this) }
        if (argType != NatType) {
            ErrorUnexpectedTypeForExpression(NatType, argType, ctx).report(parser)
        }

        return BoolType
    }

    override fun visitVar(ctx: VarContext): Type {
        val varName = ctx.name.text

        return typesContext.getType(varName) ?: ErrorUndefinedVariable(varName, ctx.parent).report(parser)
    }

    override fun visitTypeAbstraction(ctx: TypeAbstractionContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitDivide(ctx: DivideContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitLessThan(ctx: LessThanContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitDotRecord(ctx: DotRecordContext): Type {
        val exprType = typesContext.runWithExpectedType(null) { ctx.expr().accept(this) }
        if (exprType !is RecordType) {
            ErrorNotARecord(ctx.expr(), exprType).report(parser)
        }

        val fieldTypeInfo = exprType.fields.firstOrNull { it.first == ctx.label.text }
            ?: ErrorUnexpectedFieldAccess(exprType, ctx, ctx.label.text).report(parser)

        return fieldTypeInfo.second
    }

    override fun visitGreaterThan(ctx: GreaterThanContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitEqual(ctx: EqualContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitThrow(ctx: ThrowContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitMultiply(ctx: MultiplyContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitConstMemory(ctx: ConstMemoryContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitList(ctx: ListContext): Type {
        val expected = typesContext.getExpectedType()
        if (expected != null && expected !is ListType) {
            ErrorUnexpectedList(expected, ctx).report(parser)
        }

        if (ctx.exprs.isEmpty()) {
            return expected ?: ErrorAmbiguousList(ctx).report(parser)
        }

        val firstElemType = typesContext.runWithExpectedType((expected as? ListType)?.contentType) {
            ctx.exprs.first().accept(this)
        }

        typesContext.runWithExpectedType(firstElemType) {
            ctx.exprs.drop(1).forEach {
                val exprType = it.accept(this)
                if (!isUnifiable(firstElemType, exprType)) {
                    ErrorUnexpectedTypeForExpression(firstElemType, exprType, ctx).report(parser)
                }
            }
        }

        return ListType(firstElemType)
    }

    override fun visitTryCatch(ctx: TryCatchContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitHead(ctx: HeadContext): Type {
        val listType = typesContext.runWithExpectedType(null) { ctx.list.accept(this) }
        if (listType !is ListType) {
            ErrorNotAList(ctx, listType).report(parser)
        }

        return listType.contentType
    }

    override fun visitTerminatingSemicolon(ctx: TerminatingSemicolonContext): Type {
        return ctx.expr().accept(this)
    }

    override fun visitNotEqual(ctx: NotEqualContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitConstUnit(ctx: ConstUnitContext): Type {
        return UnitType
    }

    override fun visitSequence(ctx: SequenceContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitConstFalse(ctx: ConstFalseContext): Type {
        return BoolType
    }

    override fun visitAbstraction(ctx: AbstractionContext): Type {
        val expectedType = typesContext.getExpectedType()
        if (expectedType != null && expectedType !is FuncType) {
            ErrorUnexpectedLambda(expectedType, ctx).report(parser)
        }
        val expectedArgType = (expectedType as? FuncType)?.argType

        val param = ctx.paramDecl
        val paramName = param.name.text
        val paramType = typesContext.runWithExpectedType(expectedArgType) { param.stellatype().accept(this) }

        if (expectedArgType != null && !isUnifiable(paramType, expectedArgType)) {
            ErrorUnexpectedTypeForParameter(paramType, expectedArgType, ctx).report(parser)
        }

        return typesContext.runWithTypeInfo(paramName, paramType) {
            FuncType(paramType, ctx.returnExpr.accept(this))
        }
    }

    override fun visitConstInt(ctx: ConstIntContext): Type {
        return NatType
    }

    override fun visitVariant(ctx: VariantContext): Type {
        val expectedType = typesContext.getExpectedType() ?: ErrorAmbiguousVariantType(ctx).report(parser)
        val variantType = typesContext.runWithExpectedType(null) { ctx.rhs.accept(this) }
        if (expectedType !is VariantType) {
            ErrorUnexpectedVariant(expectedType, ctx).report(parser)
        }
        val variantLabel = ctx.label.text
        val expectedLabel = expectedType.variants.firstOrNull { it.first == variantLabel }
            ?: ErrorUnexpectedVariantLabel(variantLabel, variantType, expectedType, ctx).report(parser)

        if (!isUnifiable(expectedLabel.second, variantType)) {
            ErrorUnexpectedTypeForExpression(expectedLabel.second, variantType, ctx).report(parser)
        }

        return expectedType
    }

    override fun visitConstTrue(ctx: ConstTrueContext): Type {
        return BoolType
    }

    override fun visitSubtract(ctx: SubtractContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeCast(ctx: TypeCastContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitIf(ctx: IfContext): Type {
        val conditionType = typesContext.runWithExpectedType(BoolType) { ctx.condition.accept(this) }
        if (conditionType != BoolType) {
            ErrorUnexpectedTypeForExpression(BoolType, conditionType, ctx).report(parser)
        }

        val thenType = ctx.thenExpr.accept(this)
        val elseType = ctx.elseExpr.accept(this)

        if (!isUnifiable(thenType, elseType)) {
            ErrorUnexpectedTypeForExpression(thenType, elseType, ctx).report(parser)
        }

        return thenType
    }

    override fun visitApplication(ctx: ApplicationContext): Type {
        val funType = typesContext.runWithExpectedType(null) { ctx.`fun`.accept(this) }

        if (funType !is FuncType) {
            ErrorNotAFunction(ctx.`fun`, funType).report(parser)
        }

        val exprType = typesContext.runWithExpectedType(funType.argType) { ctx.expr.accept(this) }
        if (!isUnifiable(funType.argType, exprType)) {
            ErrorUnexpectedTypeForExpression(funType.argType, exprType, ctx).report(parser)
        }

        return funType.returnType
    }

    override fun visitDeref(ctx: DerefContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitIsEmpty(ctx: IsEmptyContext): Type {
        val argType = typesContext.runWithExpectedType(null) { ctx.list.accept(this) }
        if (argType !is ListType) {
            ErrorNotAList(ctx, argType).report(parser)
        }

        return BoolType
    }

    override fun visitPanic(ctx: PanicContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitLessThanOrEqual(ctx: LessThanOrEqualContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitSucc(ctx: SuccContext): Type {
        val argType = typesContext.runWithExpectedType(NatType) { ctx.n.accept(this) }
        if (argType != NatType) {
            ErrorUnexpectedTypeForExpression(NatType, argType, ctx).report(parser)
        }

        return NatType
    }

    override fun visitInl(ctx: InlContext): Type {
        val leftType = typesContext.runWithExpectedType(null) { ctx.expr().accept(this) }
        val expectedType = typesContext.getExpectedType() ?: ErrorAmbiguousSumType(ctx).report(parser)
        if (expectedType !is SumType) {
            ErrorUnexpectedInjection(expectedType, ctx).report(parser)
        }

        return SumType(leftType, expectedType.inr)
    }

    override fun visitGreaterThanOrEqual(ctx: GreaterThanOrEqualContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitInr(ctx: InrContext): Type {
        val rightType = typesContext.runWithExpectedType(null) { ctx.expr().accept(this) }
        val expectedType = typesContext.getExpectedType() ?: ErrorAmbiguousSumType(ctx).report(parser)
        if (expectedType !is SumType) {
            ErrorUnexpectedInjection(expectedType, ctx).report(parser)
        }

        return SumType(expectedType.inl, rightType)
    }

    override fun visitMatch(ctx: MatchContext): Type {
        val expressionType = typesContext.runWithExpectedType(null) { ctx.expr().accept(this) }
        val cases = ctx.cases
        if (cases.isEmpty()) {
            ErrorIllegalEmptyMatching(ctx).report(parser)
        }

        var isExhaustive = cases.any { it.pattern() is PatternVarContext }
        if (expressionType is SumType) {
            isExhaustive = isExhaustive ||
                    cases.any { it.pattern() is PatternInlContext } && cases.any { it.pattern() is PatternInrContext }
        } else if (expressionType is VariantType) {
            val casesLabels = cases.mapNotNull { (it.pattern() as? PatternVariantContext)?.label?.text }
            isExhaustive = isExhaustive ||
                    expressionType.variants.map { it.first }.all { casesLabels.contains(it) }
        }

        return typesContext.runWithExpectedType(expressionType) {
            val casesType = cases.first().accept(this)

            cases.drop(1).forEach {
                val caseType = it.accept(this)
                if (!isUnifiable(casesType, caseType)) {
                    ErrorUnexpectedTypeForExpression(casesType, caseType, ctx).report(parser)
                }
            }

            casesType
        }.also { if (!isExhaustive) {
            ErrorNonexhaustiveMatchPatterns(expressionType, ctx).report(parser)
        } }
    }

    override fun visitLogicNot(ctx: LogicNotContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitParenthesisedExpr(ctx: ParenthesisedExprContext): Type {
        return ctx.expr().accept(this)
    }

    override fun visitTail(ctx: TailContext): Type {
        val listType = typesContext.runWithExpectedType(null) { ctx.list.accept(this) }
        if (listType !is ListType) {
            ErrorNotAList(ctx, listType).report(parser)
        }

        return listType
    }

    override fun visitRecord(ctx: RecordContext): Type {
        val expected = typesContext.getExpectedType()
        if (expected != null && expected !is RecordType) {
            ErrorUnexpectedRecord(expected, ctx).report(parser)
        }

        val fields = mutableListOf<Pair<String, Type>>()
        for (binding in ctx.bindings) {
            fields.add(Pair(binding.name.text, typesContext.runWithExpectedType(null) {
                binding.rhs.accept(this)
            }))
        }

        val actualType = RecordType(fields)

        if ((expected as? RecordType)?.fields != null) {
            val unexpectedFields = fields.subtract(expected.fields.toSet())
            if (unexpectedFields.isNotEmpty()) {
                ErrorUnexpectedRecordFields(expected, actualType, ctx, unexpectedFields).report(parser)
            }

            val missingFields = expected.fields.subtract(fields.toSet())
            if (missingFields.isNotEmpty()) {
                ErrorMissingRecordFields(expected, actualType, ctx, missingFields).report(parser)
            }
        }

        return actualType
    }

    override fun visitLogicAnd(ctx: LogicAndContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeApplication(ctx: TypeApplicationContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitLetRec(ctx: LetRecContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitLogicOr(ctx: LogicOrContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTryWith(ctx: TryWithContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPred(ctx: PredContext): Type {
        val argType = typesContext.runWithExpectedType(NatType) { ctx.n.accept(this) }
        if (argType != NatType) {
            ErrorUnexpectedTypeForExpression(NatType, argType, ctx).report(parser)
        }

        return NatType
    }

    override fun visitTypeAsc(ctx: TypeAscContext): Type {
        val expectedType = ctx.stellatype().accept(this)
        val expressionType = typesContext.runWithExpectedType(expectedType) { ctx.expr().accept(this) }

        if (!isUnifiable(expectedType, expressionType)) {
            ErrorUnexpectedTypeForExpression(expectedType, expressionType, ctx).report(parser)
        }

        return expectedType
    }

    override fun visitNatRec(ctx: NatRecContext): Type {
        val nType = typesContext.runWithExpectedType(NatType) { ctx.n.accept(this) }
        if (nType != NatType) {
            ErrorUnexpectedTypeForExpression(NatType, nType, ctx).report(parser)
        }

        val zType = ctx.initial.accept(this)
        val sType = typesContext.runWithExpectedType(null) { ctx.step.accept(this) }

        val expectedSType = FuncType(NatType, FuncType(zType, zType))
        if (!isUnifiable(expectedSType, sType)) {
            ErrorUnexpectedTypeForExpression(expectedSType, sType, ctx).report(parser)
        }

        return zType
    }

    override fun visitUnfold(ctx: UnfoldContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitRef(ctx: RefContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitDotTuple(ctx: DotTupleContext): Type {
        val tupleType = typesContext.runWithExpectedType(null) { ctx.expr().accept(this) }
        if (tupleType !is TupleType) {
            ErrorNotATuple(ctx, tupleType).report(parser)
        }

        val index = ctx.index.text.toInt() - 1
        if (tupleType.types.size <= index) {
            ErrorTupleIndexOfBounds(ctx, index).report(parser)
        }

        return tupleType.types[ctx.index.text.toInt() - 1]
    }

    override fun visitFix(ctx: FixContext): Type {
        val expressionType = typesContext.runWithExpectedType(null) { ctx.expr().accept(this) }
        if (expressionType !is FuncType) {
            ErrorNotAFunction(ctx.expr(), expressionType).report(parser)
        }

        val expectedType = FuncType(expressionType.argType, expressionType.argType)
        if (!isUnifiable(expectedType, expressionType)) {
            ErrorUnexpectedTypeForExpression(expectedType, expressionType, ctx).report(parser)
        }

        return expectedType.argType
    }

    override fun visitLet(ctx: LetContext): Type {
        val patternBinding = ctx.patternBinding
        val patternBindingType = typesContext.runWithExpectedType(null) { patternBinding.expr().accept(this) }
        val patternBindingName = (patternBinding.pattern() as PatternVarContext).name.text

        return typesContext.runWithTypeInfo(patternBindingName, patternBindingType) {
            ctx.expr().accept(this)
        }
    }

    override fun visitAssign(ctx: AssignContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTuple(ctx: TupleContext): Type {
        val expected = typesContext.getExpectedType()
        if (expected != null && expected !is TupleType) {
            ErrorUnexpectedTuple(expected, ctx).report(parser)
        }

        if (expected is TupleType && expected.types.size != ctx.expr().size) {
            ErrorUnexpectedTupleLength(expected, ctx).report(parser)
        }

        return TupleType(ctx.exprs.map { it.accept(this) })
    }

    override fun visitConsList(ctx: ConsListContext): Type {
        val expected = typesContext.getExpectedType()
        if (expected != null && expected !is ListType) {
            ErrorUnexpectedList(expected, ctx).report(parser)
        }

        val headType = typesContext.runWithExpectedType((expected as? ListType)?.contentType) {
            ctx.head.accept(this)
        }
        val expectedListType = ListType(headType)
        val tailType = typesContext.runWithExpectedType(expectedListType) { ctx.tail.accept(this) }
        if (isUnifiable(expectedListType, tailType)) {
            ErrorUnexpectedTypeForExpression(expectedListType, tailType, ctx).report(parser)
        }

        return expectedListType
    }

    override fun visitPatternBinding(ctx: PatternBindingContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitBinding(ctx: BindingContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitMatchCase(ctx: MatchCaseContext): Type {
        val pattern = ctx.pattern()
        val expectedType = typesContext.getExpectedType()!!
        if (pattern is PatternVarContext) {
            return typesContext.runWithTypeInfo(pattern.name.text, expectedType) {
                ctx.expr().accept(this)
            }
        }
        if (pattern is PatternInlContext) {
            val patternName = (pattern.pattern() as PatternVarContext).name.text
            expectedType as? SumType ?:
                ErrorUnexpectedPatternForType(expectedType, pattern).report(parser)

            return typesContext.runWithTypeInfo(patternName, expectedType.inl) {
                ctx.expr().accept(this)
            }
        }
        if (pattern is PatternInrContext) {
            val patternName = (pattern.pattern() as PatternVarContext).name.text
            expectedType as? SumType ?:
                ErrorUnexpectedPatternForType(expectedType, pattern).report(parser)

            return typesContext.runWithTypeInfo(patternName, expectedType.inr) {
                ctx.expr().accept(this)
            }
        }
        if (pattern is PatternVariantContext) {
            expectedType as? VariantType
                ?: ErrorUnexpectedPatternForType(expectedType, pattern).report(parser)
            val variantType = expectedType.variants.firstOrNull { it.first == pattern.label.text }
                ?: ErrorUnexpectedPatternForType(expectedType, pattern).report(parser)

            val patternName = (pattern.pattern() as PatternVarContext).name.text

            return typesContext.runWithTypeInfo(patternName, variantType.second) {
                ctx.expr().accept(this)
            }
        }
        TODO("Not yet implemented")
    }

    override fun visitPatternVariant(ctx: PatternVariantContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternInl(ctx: PatternInlContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternInr(ctx: PatternInrContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternTuple(ctx: PatternTupleContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternRecord(ctx: PatternRecordContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternList(ctx: PatternListContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternCons(ctx: PatternConsContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternFalse(ctx: PatternFalseContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternTrue(ctx: PatternTrueContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternUnit(ctx: PatternUnitContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternInt(ctx: PatternIntContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternSucc(ctx: PatternSuccContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternVar(ctx: PatternVarContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitParenthesisedPattern(ctx: ParenthesisedPatternContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitLabelledPattern(ctx: LabelledPatternContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeTuple(ctx: TypeTupleContext): Type {
        return TupleType(ctx.types.map { it.accept(this) })
    }

    override fun visitTypeTop(ctx: TypeTopContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeBool(ctx: TypeBoolContext): Type {
        return BoolType
    }

    override fun visitTypeRef(ctx: TypeRefContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeRec(ctx: TypeRecContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeSum(ctx: TypeSumContext): Type {
        return SumType(ctx.left.accept(this), ctx.right.accept(this))
    }

    override fun visitTypeVar(ctx: TypeVarContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeVariant(ctx: TypeVariantContext): Type {
        return VariantType(ctx.fieldTypes.map { Pair(it.label.text, it.stellatype().accept(this))  })
    }

    override fun visitTypeUnit(ctx: TypeUnitContext): Type {
        return UnitType
    }

    override fun visitTypeNat(ctx: TypeNatContext): Type {
        return NatType
    }

    override fun visitTypeBottom(ctx: TypeBottomContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeParens(ctx: TypeParensContext): Type {
        return ctx.stellatype().accept(this)
    }

    override fun visitTypeFun(ctx: TypeFunContext): Type {
        val paramType = ctx.paramTypes.first().accept(this)
        val returnType = ctx.returnType.accept(this)

        return FuncType(paramType, returnType)
    }

    override fun visitTypeForAll(ctx: TypeForAllContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeRecord(ctx: TypeRecordContext): Type {
        val fields = mutableListOf<Pair<String, Type>>()
        for (field in ctx.fieldTypes) {
            fields.add(Pair(field.label.text, field.stellatype().accept(this)))
        }

        return RecordType(fields)
    }

    override fun visitTypeList(ctx: TypeListContext): Type {
        return ListType(ctx.stellatype().accept(this))
    }

    override fun visitRecordFieldType(ctx: RecordFieldTypeContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitVariantFieldType(ctx: VariantFieldTypeContext): Type {
        TODO("Not yet implemented")
    }
}