package dev.bukreev.types

import dev.bukreev.types.parsing.stellaParser.*
import dev.bukreev.types.parsing.stellaParserVisitor
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode
import org.antlr.v4.runtime.tree.TerminalNode

class TypeChecker(private val typesContext: TypesContext = TypesContext()) : stellaParserVisitor<Type> {
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

        return mainFunctionType ?: ErrorMissingMain.report()
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
            val returnExpressionType = ctx.returnExpr.accept(this)
            if (!isUnifiable(returnType, returnExpressionType)) {
                reportUnexpectedType(returnType, returnExpressionType, ctx.returnExpr)
            }

            funcType
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
        val argType = ctx.n.accept(this)
        if (argType != NatType) {
            reportUnexpectedType(NatType, argType, ctx)
        }

        return BoolType
    }

    override fun visitVar(ctx: VarContext): Type {
        val varName = ctx.name.text

        return typesContext.getType(varName) ?: ErrorUndefinedVariable(varName, ctx.parent).report()
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
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    override fun visitTryCatch(ctx: TryCatchContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitHead(ctx: HeadContext): Type {
        TODO("Not yet implemented")
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
        val param = ctx.paramDecl
        val paramName = param.name.text
        val paramType = param.stellatype().accept(this)

        return typesContext.runWithTypeInfo(paramName, paramType) {
            FuncType(paramType, ctx.returnExpr.accept(this))
        }
    }

    override fun visitConstInt(ctx: ConstIntContext): Type {
        return NatType
    }

    override fun visitVariant(ctx: VariantContext): Type {
        TODO("Not yet implemented")
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
        val conditionType = ctx.condition.accept(this)
        if (conditionType != BoolType) {
            reportUnexpectedType(BoolType, conditionType, ctx)
        }

        val thenType = ctx.thenExpr.accept(this)
        val elseType = ctx.elseExpr.accept(this)

        if (!isUnifiable(thenType, elseType)) {
            reportUnexpectedType(thenType, elseType, ctx)
        }

        return thenType
    }

    override fun visitApplication(ctx: ApplicationContext): Type {
        val funType = ctx.`fun`.accept(this)
        val exprType = ctx.expr.accept(this)

        if (funType !is FuncType) {
            ErrorNotAFunction(ctx.`fun`, funType).report()
        }

        if (!isUnifiable(funType.argType, exprType)) {
            reportUnexpectedType(funType.argType, exprType, ctx)
        }

        return funType.returnType
    }

    override fun visitDeref(ctx: DerefContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitIsEmpty(ctx: IsEmptyContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPanic(ctx: PanicContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitLessThanOrEqual(ctx: LessThanOrEqualContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitSucc(ctx: SuccContext): Type {
        val argType = ctx.n.accept(this)
        if (argType != NatType) {
            reportUnexpectedType(NatType, argType, ctx)
        }

        return NatType
    }

    override fun visitInl(ctx: InlContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitGreaterThanOrEqual(ctx: GreaterThanOrEqualContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitInr(ctx: InrContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitMatch(ctx: MatchContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitLogicNot(ctx: LogicNotContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitParenthesisedExpr(ctx: ParenthesisedExprContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTail(ctx: TailContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitRecord(ctx: RecordContext): Type {
        TODO("Not yet implemented")
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
        val argType = ctx.n.accept(this)
        if (argType != NatType) {
            reportUnexpectedType(NatType, argType, ctx)
        }

        return NatType
    }

    override fun visitTypeAsc(ctx: TypeAscContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitNatRec(ctx: NatRecContext): Type {
        val nType = ctx.n.accept(this)
        if (nType != NatType) {
            reportUnexpectedType(NatType, nType, ctx)
        }

        val zType = ctx.initial.accept(this)
        val sType = ctx.step.accept(this)

        val expectedSType = FuncType(NatType, FuncType(zType, zType))
        if (!isUnifiable(expectedSType, sType)) {
            reportUnexpectedType(expectedSType, sType, ctx)
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
        val tupleType = ctx.expr().accept(this)
        if (tupleType !is TupleType) {
            ErrorNotATuple(ctx, tupleType).report()
        }

        val index = ctx.index.text.toInt() - 1
        if (tupleType.types.size <= index) {
            ErrorTupleIndexOfBounds(ctx, index).report()
        }

        return tupleType.types[ctx.index.text.toInt() - 1]
    }

    override fun visitFix(ctx: FixContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitLet(ctx: LetContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitAssign(ctx: AssignContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTuple(ctx: TupleContext): Type {
        return TupleType(ctx.exprs.map { it.accept(this) })
    }

    override fun visitConsList(ctx: ConsListContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternBinding(ctx: PatternBindingContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitBinding(ctx: BindingContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitMatchCase(ctx: MatchCaseContext): Type {
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
        TODO("Not yet implemented")
    }

    override fun visitTypeVar(ctx: TypeVarContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeVariant(ctx: TypeVariantContext): Type {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    override fun visitTypeList(ctx: TypeListContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitRecordFieldType(ctx: RecordFieldTypeContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitVariantFieldType(ctx: VariantFieldTypeContext): Type {
        TODO("Not yet implemented")
    }
}