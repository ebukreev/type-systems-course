package dev.bukreev.types

import dev.bukreev.types.parsing.stellaParser
import dev.bukreev.types.parsing.stellaParserVisitor
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode
import org.antlr.v4.runtime.tree.TerminalNode

class TypeChecker : stellaParserVisitor<Type> {
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

    override fun visitStart_Program(ctx: stellaParser.Start_ProgramContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitStart_Expr(ctx: stellaParser.Start_ExprContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitStart_Type(ctx: stellaParser.Start_TypeContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitProgram(ctx: stellaParser.ProgramContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitLanguageCore(ctx: stellaParser.LanguageCoreContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitAnExtension(ctx: stellaParser.AnExtensionContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitDeclFun(ctx: stellaParser.DeclFunContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitDeclFunGeneric(ctx: stellaParser.DeclFunGenericContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitDeclTypeAlias(ctx: stellaParser.DeclTypeAliasContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitDeclExceptionType(ctx: stellaParser.DeclExceptionTypeContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitDeclExceptionVariant(ctx: stellaParser.DeclExceptionVariantContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitInlineAnnotation(ctx: stellaParser.InlineAnnotationContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitParamDecl(ctx: stellaParser.ParamDeclContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitFold(ctx: stellaParser.FoldContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitAdd(ctx: stellaParser.AddContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitIsZero(ctx: stellaParser.IsZeroContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitVar(ctx: stellaParser.VarContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeAbstraction(ctx: stellaParser.TypeAbstractionContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitDivide(ctx: stellaParser.DivideContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitLessThan(ctx: stellaParser.LessThanContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitDotRecord(ctx: stellaParser.DotRecordContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitGreaterThan(ctx: stellaParser.GreaterThanContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitEqual(ctx: stellaParser.EqualContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitThrow(ctx: stellaParser.ThrowContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitMultiply(ctx: stellaParser.MultiplyContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitConstMemory(ctx: stellaParser.ConstMemoryContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitList(ctx: stellaParser.ListContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTryCatch(ctx: stellaParser.TryCatchContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitHead(ctx: stellaParser.HeadContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitNotEqual(ctx: stellaParser.NotEqualContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitConstUnit(ctx: stellaParser.ConstUnitContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitSequence(ctx: stellaParser.SequenceContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitConstFalse(ctx: stellaParser.ConstFalseContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitAbstraction(ctx: stellaParser.AbstractionContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitConstInt(ctx: stellaParser.ConstIntContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitVariant(ctx: stellaParser.VariantContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitConstTrue(ctx: stellaParser.ConstTrueContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitSubtract(ctx: stellaParser.SubtractContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeCast(ctx: stellaParser.TypeCastContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitIf(ctx: stellaParser.IfContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitApplication(ctx: stellaParser.ApplicationContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitDeref(ctx: stellaParser.DerefContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitIsEmpty(ctx: stellaParser.IsEmptyContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPanic(ctx: stellaParser.PanicContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitLessThanOrEqual(ctx: stellaParser.LessThanOrEqualContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitSucc(ctx: stellaParser.SuccContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitInl(ctx: stellaParser.InlContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitGreaterThanOrEqual(ctx: stellaParser.GreaterThanOrEqualContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitInr(ctx: stellaParser.InrContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitMatch(ctx: stellaParser.MatchContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitLogicNot(ctx: stellaParser.LogicNotContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitParenthesisedExpr(ctx: stellaParser.ParenthesisedExprContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTail(ctx: stellaParser.TailContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitRecord(ctx: stellaParser.RecordContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitLogicAnd(ctx: stellaParser.LogicAndContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeApplication(ctx: stellaParser.TypeApplicationContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitLetRec(ctx: stellaParser.LetRecContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitLogicOr(ctx: stellaParser.LogicOrContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTryWith(ctx: stellaParser.TryWithContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPred(ctx: stellaParser.PredContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeAsc(ctx: stellaParser.TypeAscContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitNatRec(ctx: stellaParser.NatRecContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitUnfold(ctx: stellaParser.UnfoldContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitRef(ctx: stellaParser.RefContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitDotTuple(ctx: stellaParser.DotTupleContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitFix(ctx: stellaParser.FixContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitLet(ctx: stellaParser.LetContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitAssign(ctx: stellaParser.AssignContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTuple(ctx: stellaParser.TupleContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitConsList(ctx: stellaParser.ConsListContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternBinding(ctx: stellaParser.PatternBindingContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitBinding(ctx: stellaParser.BindingContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitMatchCase(ctx: stellaParser.MatchCaseContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternVariant(ctx: stellaParser.PatternVariantContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternInl(ctx: stellaParser.PatternInlContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternInr(ctx: stellaParser.PatternInrContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternTuple(ctx: stellaParser.PatternTupleContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternRecord(ctx: stellaParser.PatternRecordContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternList(ctx: stellaParser.PatternListContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternCons(ctx: stellaParser.PatternConsContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternFalse(ctx: stellaParser.PatternFalseContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternTrue(ctx: stellaParser.PatternTrueContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternUnit(ctx: stellaParser.PatternUnitContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternInt(ctx: stellaParser.PatternIntContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternSucc(ctx: stellaParser.PatternSuccContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternVar(ctx: stellaParser.PatternVarContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitParenthesisedPattern(ctx: stellaParser.ParenthesisedPatternContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitLabelledPattern(ctx: stellaParser.LabelledPatternContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeTuple(ctx: stellaParser.TypeTupleContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeTop(ctx: stellaParser.TypeTopContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeBool(ctx: stellaParser.TypeBoolContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeRef(ctx: stellaParser.TypeRefContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeRec(ctx: stellaParser.TypeRecContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeSum(ctx: stellaParser.TypeSumContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeVar(ctx: stellaParser.TypeVarContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeVariant(ctx: stellaParser.TypeVariantContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeUnit(ctx: stellaParser.TypeUnitContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeNat(ctx: stellaParser.TypeNatContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeBottom(ctx: stellaParser.TypeBottomContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeParens(ctx: stellaParser.TypeParensContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeFun(ctx: stellaParser.TypeFunContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeForAll(ctx: stellaParser.TypeForAllContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeRecord(ctx: stellaParser.TypeRecordContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeList(ctx: stellaParser.TypeListContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitRecordFieldType(ctx: stellaParser.RecordFieldTypeContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitVariantFieldType(ctx: stellaParser.VariantFieldTypeContext): Type {
        TODO("Not yet implemented")
    }
}