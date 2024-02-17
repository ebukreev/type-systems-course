package dev.bukreev.types

import dev.bukreev.types.parsing.stellaParser.*
import dev.bukreev.types.parsing.stellaParserListener
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.TerminalNode

class TypeChecker(val typeResolver: TypeResolver) : stellaParserListener {
    override fun visitTerminal(node: TerminalNode) {
        TODO("Not yet implemented")
    }

    override fun visitErrorNode(node: ErrorNode) {
        TODO("Not yet implemented")
    }

    override fun enterEveryRule(ctx: ParserRuleContext) {
        TODO("Not yet implemented")
    }

    override fun exitEveryRule(ctx: ParserRuleContext) {
        TODO("Not yet implemented")
    }

    override fun enterStart_Program(ctx: Start_ProgramContext) {
        TODO("Not yet implemented")
    }

    override fun exitStart_Program(ctx: Start_ProgramContext) {
        TODO("Not yet implemented")
    }

    override fun enterStart_Expr(ctx: Start_ExprContext) {
        TODO("Not yet implemented")
    }

    override fun exitStart_Expr(ctx: Start_ExprContext) {
        TODO("Not yet implemented")
    }

    override fun enterStart_Type(ctx: Start_TypeContext) {
        TODO("Not yet implemented")
    }

    override fun exitStart_Type(ctx: Start_TypeContext) {
        TODO("Not yet implemented")
    }

    override fun enterProgram(ctx: ProgramContext) {
        TODO("Not yet implemented")
    }

    override fun exitProgram(ctx: ProgramContext) {
        TODO("Not yet implemented")
    }

    override fun enterLanguageCore(ctx: LanguageCoreContext) {
        TODO("Not yet implemented")
    }

    override fun exitLanguageCore(ctx: LanguageCoreContext) {
        TODO("Not yet implemented")
    }

    override fun enterAnExtension(ctx: AnExtensionContext) {
        TODO("Not yet implemented")
    }

    override fun exitAnExtension(ctx: AnExtensionContext) {
        TODO("Not yet implemented")
    }

    override fun enterDeclFun(ctx: DeclFunContext) {
        TODO("Not yet implemented")
    }

    override fun exitDeclFun(ctx: DeclFunContext) {
        TODO("Not yet implemented")
    }

    override fun enterDeclFunGeneric(ctx: DeclFunGenericContext) {
        TODO("Not yet implemented")
    }

    override fun exitDeclFunGeneric(ctx: DeclFunGenericContext) {
        TODO("Not yet implemented")
    }

    override fun enterDeclTypeAlias(ctx: DeclTypeAliasContext) {
        TODO("Not yet implemented")
    }

    override fun exitDeclTypeAlias(ctx: DeclTypeAliasContext) {
        TODO("Not yet implemented")
    }

    override fun enterDeclExceptionType(ctx: DeclExceptionTypeContext) {
        TODO("Not yet implemented")
    }

    override fun exitDeclExceptionType(ctx: DeclExceptionTypeContext) {
        TODO("Not yet implemented")
    }

    override fun enterDeclExceptionVariant(ctx: DeclExceptionVariantContext) {
        TODO("Not yet implemented")
    }

    override fun exitDeclExceptionVariant(ctx: DeclExceptionVariantContext) {
        TODO("Not yet implemented")
    }

    override fun enterInlineAnnotation(ctx: InlineAnnotationContext) {
        TODO("Not yet implemented")
    }

    override fun exitInlineAnnotation(ctx: InlineAnnotationContext) {
        TODO("Not yet implemented")
    }

    override fun enterParamDecl(ctx: ParamDeclContext) {
        TODO("Not yet implemented")
    }

    override fun exitParamDecl(ctx: ParamDeclContext) {
        TODO("Not yet implemented")
    }

    override fun enterFold(ctx: FoldContext) {
        TODO("Not yet implemented")
    }

    override fun exitFold(ctx: FoldContext) {
        TODO("Not yet implemented")
    }

    override fun enterAdd(ctx: AddContext) {
        TODO("Not yet implemented")
    }

    override fun exitAdd(ctx: AddContext) {
        TODO("Not yet implemented")
    }

    override fun enterIsZero(ctx: IsZeroContext) {
        TODO("Not yet implemented")
    }

    override fun exitIsZero(ctx: IsZeroContext) {
        TODO("Not yet implemented")
    }

    override fun enterVar(ctx: VarContext) {
        TODO("Not yet implemented")
    }

    override fun exitVar(ctx: VarContext) {
        TODO("Not yet implemented")
    }

    override fun enterTypeAbstraction(ctx: TypeAbstractionContext) {
        TODO("Not yet implemented")
    }

    override fun exitTypeAbstraction(ctx: TypeAbstractionContext) {
        TODO("Not yet implemented")
    }

    override fun enterDivide(ctx: DivideContext) {
        TODO("Not yet implemented")
    }

    override fun exitDivide(ctx: DivideContext) {
        TODO("Not yet implemented")
    }

    override fun enterLessThan(ctx: LessThanContext) {
        TODO("Not yet implemented")
    }

    override fun exitLessThan(ctx: LessThanContext) {
        TODO("Not yet implemented")
    }

    override fun enterDotRecord(ctx: DotRecordContext) {
        TODO("Not yet implemented")
    }

    override fun exitDotRecord(ctx: DotRecordContext) {
        TODO("Not yet implemented")
    }

    override fun enterGreaterThan(ctx: GreaterThanContext) {
        TODO("Not yet implemented")
    }

    override fun exitGreaterThan(ctx: GreaterThanContext) {
        TODO("Not yet implemented")
    }

    override fun enterEqual(ctx: EqualContext) {
        TODO("Not yet implemented")
    }

    override fun exitEqual(ctx: EqualContext) {
        TODO("Not yet implemented")
    }

    override fun enterThrow(ctx: ThrowContext) {
        TODO("Not yet implemented")
    }

    override fun exitThrow(ctx: ThrowContext) {
        TODO("Not yet implemented")
    }

    override fun enterMultiply(ctx: MultiplyContext) {
        TODO("Not yet implemented")
    }

    override fun exitMultiply(ctx: MultiplyContext) {
        TODO("Not yet implemented")
    }

    override fun enterConstMemory(ctx: ConstMemoryContext) {
        TODO("Not yet implemented")
    }

    override fun exitConstMemory(ctx: ConstMemoryContext) {
        TODO("Not yet implemented")
    }

    override fun enterList(ctx: ListContext) {
        TODO("Not yet implemented")
    }

    override fun exitList(ctx: ListContext) {
        TODO("Not yet implemented")
    }

    override fun enterTryCatch(ctx: TryCatchContext) {
        TODO("Not yet implemented")
    }

    override fun exitTryCatch(ctx: TryCatchContext) {
        TODO("Not yet implemented")
    }

    override fun enterHead(ctx: HeadContext) {
        TODO("Not yet implemented")
    }

    override fun exitHead(ctx: HeadContext) {
        TODO("Not yet implemented")
    }

    override fun enterNotEqual(ctx: NotEqualContext) {
        TODO("Not yet implemented")
    }

    override fun exitNotEqual(ctx: NotEqualContext) {
        TODO("Not yet implemented")
    }

    override fun enterConstUnit(ctx: ConstUnitContext) {
        TODO("Not yet implemented")
    }

    override fun exitConstUnit(ctx: ConstUnitContext) {
        TODO("Not yet implemented")
    }

    override fun enterSequence(ctx: SequenceContext) {
        TODO("Not yet implemented")
    }

    override fun exitSequence(ctx: SequenceContext) {
        TODO("Not yet implemented")
    }

    override fun enterConstFalse(ctx: ConstFalseContext) {
        TODO("Not yet implemented")
    }

    override fun exitConstFalse(ctx: ConstFalseContext) {
        TODO("Not yet implemented")
    }

    override fun enterAbstraction(ctx: AbstractionContext) {
        TODO("Not yet implemented")
    }

    override fun exitAbstraction(ctx: AbstractionContext) {
        TODO("Not yet implemented")
    }

    override fun enterConstInt(ctx: ConstIntContext) {
        TODO("Not yet implemented")
    }

    override fun exitConstInt(ctx: ConstIntContext) {
        TODO("Not yet implemented")
    }

    override fun enterVariant(ctx: VariantContext) {
        TODO("Not yet implemented")
    }

    override fun exitVariant(ctx: VariantContext) {
        TODO("Not yet implemented")
    }

    override fun enterConstTrue(ctx: ConstTrueContext) {
        TODO("Not yet implemented")
    }

    override fun exitConstTrue(ctx: ConstTrueContext) {
        TODO("Not yet implemented")
    }

    override fun enterSubtract(ctx: SubtractContext) {
        TODO("Not yet implemented")
    }

    override fun exitSubtract(ctx: SubtractContext) {
        TODO("Not yet implemented")
    }

    override fun enterTypeCast(ctx: TypeCastContext) {
        TODO("Not yet implemented")
    }

    override fun exitTypeCast(ctx: TypeCastContext) {
        TODO("Not yet implemented")
    }

    override fun enterIf(ctx: IfContext) {
        TODO("Not yet implemented")
    }

    override fun exitIf(ctx: IfContext) {
        TODO("Not yet implemented")
    }

    override fun enterApplication(ctx: ApplicationContext) {
        TODO("Not yet implemented")
    }

    override fun exitApplication(ctx: ApplicationContext) {
        TODO("Not yet implemented")
    }

    override fun enterDeref(ctx: DerefContext) {
        TODO("Not yet implemented")
    }

    override fun exitDeref(ctx: DerefContext) {
        TODO("Not yet implemented")
    }

    override fun enterIsEmpty(ctx: IsEmptyContext) {
        TODO("Not yet implemented")
    }

    override fun exitIsEmpty(ctx: IsEmptyContext) {
        TODO("Not yet implemented")
    }

    override fun enterPanic(ctx: PanicContext) {
        TODO("Not yet implemented")
    }

    override fun exitPanic(ctx: PanicContext) {
        TODO("Not yet implemented")
    }

    override fun enterLessThanOrEqual(ctx: LessThanOrEqualContext) {
        TODO("Not yet implemented")
    }

    override fun exitLessThanOrEqual(ctx: LessThanOrEqualContext) {
        TODO("Not yet implemented")
    }

    override fun enterSucc(ctx: SuccContext) {
        TODO("Not yet implemented")
    }

    override fun exitSucc(ctx: SuccContext) {
        TODO("Not yet implemented")
    }

    override fun enterInl(ctx: InlContext) {
        TODO("Not yet implemented")
    }

    override fun exitInl(ctx: InlContext) {
        TODO("Not yet implemented")
    }

    override fun enterGreaterThanOrEqual(ctx: GreaterThanOrEqualContext) {
        TODO("Not yet implemented")
    }

    override fun exitGreaterThanOrEqual(ctx: GreaterThanOrEqualContext) {
        TODO("Not yet implemented")
    }

    override fun enterInr(ctx: InrContext) {
        TODO("Not yet implemented")
    }

    override fun exitInr(ctx: InrContext) {
        TODO("Not yet implemented")
    }

    override fun enterMatch(ctx: MatchContext) {
        TODO("Not yet implemented")
    }

    override fun exitMatch(ctx: MatchContext) {
        TODO("Not yet implemented")
    }

    override fun enterLogicNot(ctx: LogicNotContext) {
        TODO("Not yet implemented")
    }

    override fun exitLogicNot(ctx: LogicNotContext) {
        TODO("Not yet implemented")
    }

    override fun enterParenthesisedExpr(ctx: ParenthesisedExprContext) {
        TODO("Not yet implemented")
    }

    override fun exitParenthesisedExpr(ctx: ParenthesisedExprContext) {
        TODO("Not yet implemented")
    }

    override fun enterTail(ctx: TailContext) {
        TODO("Not yet implemented")
    }

    override fun exitTail(ctx: TailContext) {
        TODO("Not yet implemented")
    }

    override fun enterRecord(ctx: RecordContext) {
        TODO("Not yet implemented")
    }

    override fun exitRecord(ctx: RecordContext) {
        TODO("Not yet implemented")
    }

    override fun enterLogicAnd(ctx: LogicAndContext) {
        TODO("Not yet implemented")
    }

    override fun exitLogicAnd(ctx: LogicAndContext) {
        TODO("Not yet implemented")
    }

    override fun enterTypeApplication(ctx: TypeApplicationContext) {
        TODO("Not yet implemented")
    }

    override fun exitTypeApplication(ctx: TypeApplicationContext) {
        TODO("Not yet implemented")
    }

    override fun enterLetRec(ctx: LetRecContext) {
        TODO("Not yet implemented")
    }

    override fun exitLetRec(ctx: LetRecContext) {
        TODO("Not yet implemented")
    }

    override fun enterLogicOr(ctx: LogicOrContext) {
        TODO("Not yet implemented")
    }

    override fun exitLogicOr(ctx: LogicOrContext) {
        TODO("Not yet implemented")
    }

    override fun enterTryWith(ctx: TryWithContext) {
        TODO("Not yet implemented")
    }

    override fun exitTryWith(ctx: TryWithContext) {
        TODO("Not yet implemented")
    }

    override fun enterPred(ctx: PredContext) {
        TODO("Not yet implemented")
    }

    override fun exitPred(ctx: PredContext) {
        TODO("Not yet implemented")
    }

    override fun enterTypeAsc(ctx: TypeAscContext) {
        TODO("Not yet implemented")
    }

    override fun exitTypeAsc(ctx: TypeAscContext) {
        TODO("Not yet implemented")
    }

    override fun enterNatRec(ctx: NatRecContext) {
        TODO("Not yet implemented")
    }

    override fun exitNatRec(ctx: NatRecContext) {
        TODO("Not yet implemented")
    }

    override fun enterUnfold(ctx: UnfoldContext) {
        TODO("Not yet implemented")
    }

    override fun exitUnfold(ctx: UnfoldContext) {
        TODO("Not yet implemented")
    }

    override fun enterRef(ctx: RefContext) {
        TODO("Not yet implemented")
    }

    override fun exitRef(ctx: RefContext) {
        TODO("Not yet implemented")
    }

    override fun enterDotTuple(ctx: DotTupleContext) {
        TODO("Not yet implemented")
    }

    override fun exitDotTuple(ctx: DotTupleContext) {
        TODO("Not yet implemented")
    }

    override fun enterFix(ctx: FixContext) {
        TODO("Not yet implemented")
    }

    override fun exitFix(ctx: FixContext) {
        TODO("Not yet implemented")
    }

    override fun enterLet(ctx: LetContext) {
        TODO("Not yet implemented")
    }

    override fun exitLet(ctx: LetContext) {
        TODO("Not yet implemented")
    }

    override fun enterAssign(ctx: AssignContext) {
        TODO("Not yet implemented")
    }

    override fun exitAssign(ctx: AssignContext) {
        TODO("Not yet implemented")
    }

    override fun enterTuple(ctx: TupleContext) {
        TODO("Not yet implemented")
    }

    override fun exitTuple(ctx: TupleContext) {
        TODO("Not yet implemented")
    }

    override fun enterConsList(ctx: ConsListContext) {
        TODO("Not yet implemented")
    }

    override fun exitConsList(ctx: ConsListContext) {
        TODO("Not yet implemented")
    }

    override fun enterPatternBinding(ctx: PatternBindingContext) {
        TODO("Not yet implemented")
    }

    override fun exitPatternBinding(ctx: PatternBindingContext) {
        TODO("Not yet implemented")
    }

    override fun enterBinding(ctx: BindingContext) {
        TODO("Not yet implemented")
    }

    override fun exitBinding(ctx: BindingContext) {
        TODO("Not yet implemented")
    }

    override fun enterMatchCase(ctx: MatchCaseContext) {
        TODO("Not yet implemented")
    }

    override fun exitMatchCase(ctx: MatchCaseContext) {
        TODO("Not yet implemented")
    }

    override fun enterPatternVariant(ctx: PatternVariantContext) {
        TODO("Not yet implemented")
    }

    override fun exitPatternVariant(ctx: PatternVariantContext) {
        TODO("Not yet implemented")
    }

    override fun enterPatternInl(ctx: PatternInlContext) {
        TODO("Not yet implemented")
    }

    override fun exitPatternInl(ctx: PatternInlContext) {
        TODO("Not yet implemented")
    }

    override fun enterPatternInr(ctx: PatternInrContext) {
        TODO("Not yet implemented")
    }

    override fun exitPatternInr(ctx: PatternInrContext) {
        TODO("Not yet implemented")
    }

    override fun enterPatternTuple(ctx: PatternTupleContext) {
        TODO("Not yet implemented")
    }

    override fun exitPatternTuple(ctx: PatternTupleContext) {
        TODO("Not yet implemented")
    }

    override fun enterPatternRecord(ctx: PatternRecordContext) {
        TODO("Not yet implemented")
    }

    override fun exitPatternRecord(ctx: PatternRecordContext) {
        TODO("Not yet implemented")
    }

    override fun enterPatternList(ctx: PatternListContext) {
        TODO("Not yet implemented")
    }

    override fun exitPatternList(ctx: PatternListContext) {
        TODO("Not yet implemented")
    }

    override fun enterPatternCons(ctx: PatternConsContext) {
        TODO("Not yet implemented")
    }

    override fun exitPatternCons(ctx: PatternConsContext) {
        TODO("Not yet implemented")
    }

    override fun enterPatternFalse(ctx: PatternFalseContext) {
        TODO("Not yet implemented")
    }

    override fun exitPatternFalse(ctx: PatternFalseContext) {
        TODO("Not yet implemented")
    }

    override fun enterPatternTrue(ctx: PatternTrueContext) {
        TODO("Not yet implemented")
    }

    override fun exitPatternTrue(ctx: PatternTrueContext) {
        TODO("Not yet implemented")
    }

    override fun enterPatternUnit(ctx: PatternUnitContext) {
        TODO("Not yet implemented")
    }

    override fun exitPatternUnit(ctx: PatternUnitContext) {
        TODO("Not yet implemented")
    }

    override fun enterPatternInt(ctx: PatternIntContext) {
        TODO("Not yet implemented")
    }

    override fun exitPatternInt(ctx: PatternIntContext) {
        TODO("Not yet implemented")
    }

    override fun enterPatternSucc(ctx: PatternSuccContext) {
        TODO("Not yet implemented")
    }

    override fun exitPatternSucc(ctx: PatternSuccContext) {
        TODO("Not yet implemented")
    }

    override fun enterPatternVar(ctx: PatternVarContext) {
        TODO("Not yet implemented")
    }

    override fun exitPatternVar(ctx: PatternVarContext) {
        TODO("Not yet implemented")
    }

    override fun enterParenthesisedPattern(ctx: ParenthesisedPatternContext) {
        TODO("Not yet implemented")
    }

    override fun exitParenthesisedPattern(ctx: ParenthesisedPatternContext) {
        TODO("Not yet implemented")
    }

    override fun enterLabelledPattern(ctx: LabelledPatternContext) {
        TODO("Not yet implemented")
    }

    override fun exitLabelledPattern(ctx: LabelledPatternContext) {
        TODO("Not yet implemented")
    }

    override fun enterTypeTuple(ctx: TypeTupleContext) {
        TODO("Not yet implemented")
    }

    override fun exitTypeTuple(ctx: TypeTupleContext) {
        TODO("Not yet implemented")
    }

    override fun enterTypeTop(ctx: TypeTopContext) {
        TODO("Not yet implemented")
    }

    override fun exitTypeTop(ctx: TypeTopContext) {
        TODO("Not yet implemented")
    }

    override fun enterTypeBool(ctx: TypeBoolContext) {
        TODO("Not yet implemented")
    }

    override fun exitTypeBool(ctx: TypeBoolContext) {
        TODO("Not yet implemented")
    }

    override fun enterTypeRef(ctx: TypeRefContext) {
        TODO("Not yet implemented")
    }

    override fun exitTypeRef(ctx: TypeRefContext) {
        TODO("Not yet implemented")
    }

    override fun enterTypeRec(ctx: TypeRecContext) {
        TODO("Not yet implemented")
    }

    override fun exitTypeRec(ctx: TypeRecContext) {
        TODO("Not yet implemented")
    }

    override fun enterTypeSum(ctx: TypeSumContext) {
        TODO("Not yet implemented")
    }

    override fun exitTypeSum(ctx: TypeSumContext) {
        TODO("Not yet implemented")
    }

    override fun enterTypeVar(ctx: TypeVarContext) {
        TODO("Not yet implemented")
    }

    override fun exitTypeVar(ctx: TypeVarContext) {
        TODO("Not yet implemented")
    }

    override fun enterTypeVariant(ctx: TypeVariantContext) {
        TODO("Not yet implemented")
    }

    override fun exitTypeVariant(ctx: TypeVariantContext) {
        TODO("Not yet implemented")
    }

    override fun enterTypeUnit(ctx: TypeUnitContext) {
        TODO("Not yet implemented")
    }

    override fun exitTypeUnit(ctx: TypeUnitContext) {
        TODO("Not yet implemented")
    }

    override fun enterTypeNat(ctx: TypeNatContext) {
        TODO("Not yet implemented")
    }

    override fun exitTypeNat(ctx: TypeNatContext) {
        TODO("Not yet implemented")
    }

    override fun enterTypeBottom(ctx: TypeBottomContext) {
        TODO("Not yet implemented")
    }

    override fun exitTypeBottom(ctx: TypeBottomContext) {
        TODO("Not yet implemented")
    }

    override fun enterTypeParens(ctx: TypeParensContext) {
        TODO("Not yet implemented")
    }

    override fun exitTypeParens(ctx: TypeParensContext) {
        TODO("Not yet implemented")
    }

    override fun enterTypeFun(ctx: TypeFunContext) {
        TODO("Not yet implemented")
    }

    override fun exitTypeFun(ctx: TypeFunContext) {
        TODO("Not yet implemented")
    }

    override fun enterTypeForAll(ctx: TypeForAllContext) {
        TODO("Not yet implemented")
    }

    override fun exitTypeForAll(ctx: TypeForAllContext) {
        TODO("Not yet implemented")
    }

    override fun enterTypeRecord(ctx: TypeRecordContext) {
        TODO("Not yet implemented")
    }

    override fun exitTypeRecord(ctx: TypeRecordContext) {
        TODO("Not yet implemented")
    }

    override fun enterTypeList(ctx: TypeListContext) {
        TODO("Not yet implemented")
    }

    override fun exitTypeList(ctx: TypeListContext) {
        TODO("Not yet implemented")
    }

    override fun enterRecordFieldType(ctx: RecordFieldTypeContext) {
        TODO("Not yet implemented")
    }

    override fun exitRecordFieldType(ctx: RecordFieldTypeContext) {
        TODO("Not yet implemented")
    }

    override fun enterVariantFieldType(ctx: VariantFieldTypeContext) {
        TODO("Not yet implemented")
    }

    override fun exitVariantFieldType(ctx: VariantFieldTypeContext) {
        TODO("Not yet implemented")
    }
}