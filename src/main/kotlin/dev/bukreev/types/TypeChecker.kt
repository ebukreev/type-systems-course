package dev.bukreev.types

import dev.bukreev.types.parsing.stellaParser
import dev.bukreev.types.parsing.stellaParser.*
import dev.bukreev.types.parsing.stellaParserVisitor
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode
import org.antlr.v4.runtime.tree.TerminalNode

class TypeChecker(
    private val parser: stellaParser,
    private val typesContext: TypesContext = TypesContext(),
    private val exceptionsContext: ExceptionsContext = ExceptionsContext()
) : stellaParserVisitor<Type> {
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
        ctx.extensions.forEach { it.accept(this) }

        var mainFunctionType: Type? = null
        for (decl in ctx.decls) {
            val type = decl.accept(this)
            if (decl is DeclFunContext) {
                typesContext.addTypeInfo(decl.name.text, type)
                if (decl.name.text == "main") {
                    mainFunctionType = type
                }
            }
        }

        if (mainFunctionType == null) {
            ErrorMissingMain.report(parser)
        }

        val mainArgsSize = (mainFunctionType as FuncType).argTypes.size
        if (mainArgsSize != 1) {
            ErrorIncorrectArityOfMain(mainArgsSize).report(parser)
        }

        return mainFunctionType
    }

    override fun visitLanguageCore(ctx: LanguageCoreContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitAnExtension(ctx: AnExtensionContext): Type {
        ExtensionsContext.addExtensions(ctx.extensionNames.map { it.text })
        return UnitType
    }

    override fun visitDeclFun(ctx: DeclFunContext): Type {
        val params = ctx.paramDecls
        val paramsInfo = params.map { Pair(it.name.text, it.paramType.accept(this)) }
        val returnType = ctx.returnType.accept(this)
        val funcType = FuncType(paramsInfo.map { it.second }, returnType)

        return typesContext.runWithTypesInfo<Type>(paramsInfo.plus(Pair(ctx.name.text, funcType))) {
            val nestedFunctions = ctx.localDecls.filterIsInstance<DeclFunContext>().map {
                Pair(it.name.text, it.accept(this))
            }
            typesContext.runWithTypesInfo(nestedFunctions) {
                typesContext.runWithExpectedType(returnType) {
                    val returnExpressionType = ctx.returnExpr.accept(this)
                    if (!returnExpressionType.isApplicable(returnType)) {
                        reportUnexpectedType(
                            returnType,
                            returnExpressionType,
                            ctx.returnExpr,
                            parser
                        )
                    }

                    funcType
                }
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
        val type = ctx.exceptionType.accept(this)
        exceptionsContext.declaredExceptionsType = type

        return type
    }

    override fun visitDeclExceptionVariant(ctx: DeclExceptionVariantContext): Type {
        val type = ctx.stellatype().accept(this)
        val label = ctx.name.text

        exceptionsContext.declaredExceptionsType =
            VariantType((
                    (exceptionsContext.declaredExceptionsType as? VariantType)?.variants ?: emptyList())
                    + (label to type)
            )

        return type
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
        if (!argType.isApplicable(NatType)) {
            reportUnexpectedType(NatType, argType, ctx, parser)
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
        val declaredExceptionType = exceptionsContext.declaredExceptionsType
            ?: ErrorExceptionTypeNotDeclared(ctx).report(parser)

        val exprType = typesContext.runWithExpectedType(declaredExceptionType) { ctx.expr().accept(this) }
        if (!exprType.isApplicable(declaredExceptionType)) {
            reportUnexpectedType(declaredExceptionType, exprType, ctx, parser)
        }

        return typesContext.getExpectedType() ?:
            if (ExtensionsContext.hasAmbiguousTypeAsBottom())
                Bot
            else
                ErrorAmbiguousThrowType(ctx).report(parser)
    }

    override fun visitMultiply(ctx: MultiplyContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitConstMemory(ctx: ConstMemoryContext): Type {
        val expectedType = typesContext.getExpectedType() ?: ErrorAmbiguousReferenceType(ctx).report(parser)
        if (expectedType !is RefType) {
            ErrorUnexpectedMemoryAddress(ctx, expectedType).report(parser)
        }

        return expectedType
    }

    override fun visitList(ctx: ListContext): Type {
        val expected = typesContext.getExpectedType()
        if (expected != null && expected !is ListType) {
            ErrorUnexpectedList(expected, ctx).report(parser)
        }

        if (ctx.exprs.isEmpty()) {
            return expected ?:
                if (ExtensionsContext.hasAmbiguousTypeAsBottom())
                    ListType(Bot)
                else
                    ErrorAmbiguousList(ctx).report(parser)
        }

        val firstElemType = typesContext.runWithExpectedType((expected as? ListType)?.contentType) {
            ctx.exprs.first().accept(this)
        }

        typesContext.runWithExpectedType(firstElemType) {
            ctx.exprs.drop(1).forEach {
                val exprType = it.accept(this)
                if (!exprType.isApplicable(firstElemType)) {
                    reportUnexpectedType(firstElemType, exprType, ctx, parser)
                }
            }
        }

        return ListType(firstElemType)
    }

    override fun visitTryCatch(ctx: TryCatchContext): Type {
        val tryType = ctx.tryExpr.accept(this)
        val variablesFromPattern = getVariablesInfoFromPattern(ctx.pattern(),
            exceptionsContext.declaredExceptionsType ?: ErrorExceptionTypeNotDeclared(ctx).report(parser))

        val fallbackType = typesContext.runWithTypesInfo(variablesFromPattern) {
            typesContext.runWithExpectedType(tryType) { ctx.fallbackExpr.accept(this) }
        }

        if (!fallbackType.isApplicable(tryType)) {
            reportUnexpectedType(tryType, fallbackType, ctx, parser)
        }

        return tryType
    }

    override fun visitTryCastAs(ctx: TryCastAsContext): Type {
        typesContext.runWithExpectedType(null) { ctx.tryExpr.accept(this) }
        val variablesFromPattern = getVariablesInfoFromPattern(ctx.pattern(), ctx.type_.accept(this))

        val bodyType = typesContext.runWithTypesInfo(variablesFromPattern) { ctx.expr_.accept(this) }
        val fallbackType = typesContext.runWithExpectedType(bodyType) { ctx.fallbackExpr.accept(this) }

        if (!fallbackType.isApplicable(bodyType)) {
            reportUnexpectedType(fallbackType, bodyType, ctx, parser)
        }

        return bodyType
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
        val leftType = typesContext.runWithExpectedType(UnitType) { ctx.expr1.accept(this) }
        if (!leftType.isApplicable(UnitType)) {
            reportUnexpectedType(UnitType, leftType, ctx.expr1, parser)
        }

        return ctx.expr2.accept(this)
    }

    override fun visitConstFalse(ctx: ConstFalseContext): Type {
        return BoolType
    }

    override fun visitAbstraction(ctx: AbstractionContext): Type {
        val expectedType = typesContext.getExpectedType()
        if (expectedType != null && expectedType !is FuncType) {
            ErrorUnexpectedLambda(expectedType, ctx).report(parser)
        }
        val expectedArgTypes = (expectedType as? FuncType)?.argTypes

        val params = ctx.paramDecls
        if (expectedArgTypes != null && expectedArgTypes.size != params.size) {
            ErrorUnexpectedNumberOfParametersInLambda(expectedArgTypes.size, ctx).report(parser)
        }

        val paramsInfo = mutableListOf<Pair<String, Type>>()
        for (i in params.indices) {
            val name = params[i].name.text
            val expectedParamType = expectedArgTypes?.get(i)
            val paramType = typesContext.runWithExpectedType(expectedParamType) { params[i].stellatype().accept(this) }

            if (expectedParamType != null && !expectedParamType.isApplicable(paramType)) {
                ErrorUnexpectedTypeForParameter(paramType, expectedParamType, ctx).report(parser)
            }

            paramsInfo.add(Pair(name, paramType))
        }

        return typesContext.runWithTypesInfo(paramsInfo) {
            FuncType(paramsInfo.map { it.second }, typesContext.runWithExpectedType((expectedType as? FuncType)?.returnType) {
                ctx.returnExpr.accept(this)
            })
        }
    }

    override fun visitConstInt(ctx: ConstIntContext): Type {
        return NatType
    }

    override fun visitVariant(ctx: VariantContext): Type {
        val expectedType = typesContext.getExpectedType()
            ?: if (ExtensionsContext.hasStructuralSubtyping()) {
                return VariantType(listOf(ctx.label.text to
                    ctx.rhs?.let { typesContext.runWithExpectedType(null) { it.accept(this) } }))
            } else {
                ErrorAmbiguousVariantType(ctx).report(parser)
            }
        if (expectedType !is VariantType) {
            ErrorUnexpectedVariant(expectedType, ctx).report(parser)
        }
        val variantLabel = ctx.label.text
        val expectedLabel = expectedType.variants.firstOrNull { it.first == variantLabel }
            ?: ErrorUnexpectedVariantLabel(variantLabel, expectedType, ctx).report(parser)

        if (expectedLabel.second == null && ctx.rhs != null) {
            ErrorUnexpectedDataForNullaryLabel(ctx, expectedType).report(parser)
        }
        if (expectedLabel.second != null && ctx.rhs == null) {
            ErrorMissingDataForLabel(ctx, expectedType).report(parser)
        }

        if (ctx.rhs != null) {
            val variantType = typesContext.runWithExpectedType(expectedLabel.second) { ctx.rhs.accept(this) }

            if (!variantType.isApplicable(expectedLabel.second!!)) {
                reportUnexpectedType(expectedLabel.second!!, variantType, ctx, parser)
            }
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
        typesContext.runWithExpectedType(null) { ctx.expr().accept(this) }
        return ctx.stellatype().accept(this)
    }

    override fun visitIf(ctx: IfContext): Type {
        val conditionType = typesContext.runWithExpectedType(BoolType) { ctx.condition.accept(this) }
        if (!conditionType.isApplicable(BoolType)) {
            reportUnexpectedType(BoolType, conditionType, ctx, parser)
        }

        val thenType = ctx.thenExpr.accept(this)
        val elseType = ctx.elseExpr.accept(this)

        if (!elseType.isApplicable(thenType)) {
            reportUnexpectedType(thenType, elseType, ctx, parser)
        }

        return thenType
    }

    override fun visitApplication(ctx: ApplicationContext): Type {
        val funType = typesContext.runWithExpectedType(null) { ctx.`fun`.accept(this) }

        if (funType !is FuncType) {
            ErrorNotAFunction(ctx.`fun`, funType).report(parser)
        }

        if (funType.argTypes.size != ctx.args.size) {
            ErrorIncorrectNumberOfArguments(ctx.args.size, funType.argTypes.size, ctx).report(parser)
        }
        for (i in funType.argTypes.indices) {
            val expectedType = funType.argTypes[i]
            val exprType = typesContext.runWithExpectedType(expectedType) { ctx.args[i].accept(this) }
            if (!exprType.isApplicable(expectedType)) {
                reportUnexpectedType(expectedType, exprType, ctx, parser)
            }
        }

        return funType.returnType
    }

    override fun visitDeref(ctx: DerefContext): Type {
        val expressionType = typesContext.runWithExpectedType(typesContext.getExpectedType()?.let { RefType(it) }) {
            ctx.expr().accept(this)
        }
        if (expressionType !is RefType) {
            ErrorNotAReference(ctx).report(parser)
        }

        return expressionType.nestedType
    }

    override fun visitIsEmpty(ctx: IsEmptyContext): Type {
        val argType = typesContext.runWithExpectedType(null) { ctx.list.accept(this) }
        if (argType !is ListType) {
            ErrorNotAList(ctx, argType).report(parser)
        }

        return BoolType
    }

    override fun visitPanic(ctx: PanicContext): Type {
        return typesContext.getExpectedType() ?:
            if (ExtensionsContext.hasAmbiguousTypeAsBottom())
                Bot
            else
                ErrorAmbiguousPanicType(ctx).report(parser)
    }

    override fun visitLessThanOrEqual(ctx: LessThanOrEqualContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitSucc(ctx: SuccContext): Type {
        val argType = typesContext.runWithExpectedType(NatType) { ctx.n.accept(this) }
        if (!argType.isApplicable(NatType)) {
            reportUnexpectedType(NatType, argType, ctx, parser)
        }

        return NatType
    }

    override fun visitInl(ctx: InlContext): Type {
        val expectedType = typesContext.getExpectedType()
        if (expectedType == null && !ExtensionsContext.hasAmbiguousTypeAsBottom()) {
            ErrorAmbiguousSumType(ctx).report(parser)
        }
        if (expectedType != null && expectedType !is SumType) {
            ErrorUnexpectedInjection(expectedType, ctx).report(parser)
        }

        val leftType = typesContext.runWithExpectedType((expectedType as? SumType)?.inl) {
            ctx.expr().accept(this)
        }

        return SumType(leftType, (expectedType as? SumType)?.inr ?: Bot)
    }

    override fun visitGreaterThanOrEqual(ctx: GreaterThanOrEqualContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitInr(ctx: InrContext): Type {
        val expectedType = typesContext.getExpectedType()
        if (expectedType == null && !ExtensionsContext.hasAmbiguousTypeAsBottom()) {
            ErrorAmbiguousSumType(ctx).report(parser)
        }
        if (expectedType != null && expectedType !is SumType) {
            ErrorUnexpectedInjection(expectedType, ctx).report(parser)
        }

        val rightType = typesContext.runWithExpectedType((expectedType as? SumType)?.inr) {
            ctx.expr().accept(this)
        }

        return SumType((expectedType as? SumType)?.inl ?: Bot, rightType)
    }

    override fun visitMatch(ctx: MatchContext): Type {
        val expressionType = typesContext.runWithExpectedType(null) { ctx.expr().accept(this) }
        val cases = ctx.cases
        if (cases.isEmpty()) {
            ErrorIllegalEmptyMatching(ctx).report(parser)
        }

        val isExhaustive = ExhaustivenessChecker.isExhaustive(cases.map { it.pattern() }, expressionType, this)
        val casesType = processMatchCase(cases.first(), expressionType)

        cases.drop(1).forEach {
            val caseType = processMatchCase(it, expressionType)
            if (!caseType.isApplicable(casesType)) {
                reportUnexpectedType(casesType, caseType, ctx, parser)
            }
        }

        if (!isExhaustive) {
            ErrorNonexhaustiveMatchPatterns(expressionType, ctx).report(parser)
        }

        return casesType
    }

    private fun processMatchCase(ctx: MatchCaseContext, expressionType: Type): Type {
        val pattern = ctx.pattern()
        val variables = getVariablesInfoFromPattern(pattern, expressionType)
        val duplicate = variables.map { it.first }.groupingBy { it }.eachCount().asIterable()
            .firstOrNull { it.value > 1 }
        if (duplicate != null) {
            ErrorDuplicatePatternVariable(pattern, duplicate.key).report(parser)
        }
        return typesContext.runWithTypesInfo(variables) {
            ctx.expr().accept(this)
        }
    }

    private fun getVariablesInfoFromPattern(pattern: PatternContext, type: Type): List<Pair<String, Type>> {
        return when (pattern) {
            is PatternVarContext -> listOf(Pair(pattern.name.text, type))

            is ParenthesisedPatternContext -> getVariablesInfoFromPattern(pattern.pattern(), type)

            is PatternFalseContext -> {
                if (type != BoolType) {
                    ErrorUnexpectedPatternForType(type, pattern).report(parser)
                }
                listOf()
            }

            is PatternTrueContext -> {
                if (type != BoolType) {
                    ErrorUnexpectedPatternForType(type, pattern).report(parser)
                }
                listOf()
            }

            is PatternUnitContext -> {
                if (type != UnitType) {
                    ErrorUnexpectedPatternForType(type, pattern).report(parser)
                }
                listOf()
            }

            is PatternInlContext -> {
                if (type !is SumType) {
                    ErrorUnexpectedPatternForType(type, pattern).report(parser)
                }
                getVariablesInfoFromPattern(pattern.pattern(), type.inl)
            }

            is PatternInrContext -> {
                if (type !is SumType) {
                    ErrorUnexpectedPatternForType(type, pattern).report(parser)
                }
                getVariablesInfoFromPattern(pattern.pattern(), type.inr)
            }

            is PatternIntContext -> {
                if (type != NatType) {
                    ErrorUnexpectedPatternForType(type, pattern).report(parser)
                }
                listOf()
            }

            is PatternSuccContext -> {
                if (type != NatType) {
                    ErrorUnexpectedPatternForType(type, pattern).report(parser)
                }
                getVariablesInfoFromPattern(pattern.pattern(), type)
            }

            is PatternRecordContext -> {
                if (type !is RecordType || pattern.patterns.map { it.label.text }.toSet() !=
                    type.fields.map { it.first }.toSet()) {
                    ErrorUnexpectedPatternForType(type, pattern).report(parser)
                }

                buildList { pattern.patterns.forEach { addAll(getVariablesInfoFromPattern(it.pattern(),
                    type.fields.first { f -> f.first == it.label.text }.second)) } }
            }

            is PatternTupleContext -> {
                if (type !is TupleType || type.types.size != pattern.patterns.size) {
                    ErrorUnexpectedPatternForType(type, pattern).report(parser)
                }

                pattern.patterns.withIndex().flatMap {
                    getVariablesInfoFromPattern(it.value, type.types[it.index])
                }
            }

            is PatternVariantContext -> {
                if (type !is VariantType || !type.variants.any { it.first == pattern.label.text }) {
                    ErrorUnexpectedPatternForType(type, pattern).report(parser)
                }
                val labelType = type.variants.first { it.first == pattern.label.text }.second
                if (labelType != null && pattern.pattern() == null) {
                    ErrorUnexpectedNullaryVariantPattern(pattern, type).report(parser)
                }
                if (labelType == null && pattern.pattern() != null) {
                    ErrorUnexpectedNonNullaryVariantPattern(pattern, type).report(parser)
                }
                labelType?.let {
                    getVariablesInfoFromPattern(pattern.pattern(), it)
                } ?: listOf()
            }

            is PatternListContext -> {
                if (type !is ListType) {
                    ErrorUnexpectedPatternForType(type, pattern).report(parser)
                }
                pattern.patterns.flatMap { getVariablesInfoFromPattern(it, type.contentType) }
            }

            is PatternConsContext -> {
                if (type !is ListType) {
                    ErrorUnexpectedPatternForType(type, pattern).report(parser)
                }
                buildList {
                    addAll(getVariablesInfoFromPattern(pattern.head, type.contentType))
                    addAll(getVariablesInfoFromPattern(pattern.tail, type))
                }
            }

            is PatternAscContext -> getVariablesInfoFromPattern(pattern.pattern(), type)

            is PatternCastAsContext -> getVariablesInfoFromPattern(pattern.pattern(), type).map { it.first to
                    pattern.stellatype().accept(this) }

            else -> throw IllegalStateException()
        }
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
            val expectedType = (expected as? RecordType)?.fields?.firstOrNull { it.first == binding.name.text }?.second
            fields.add(Pair(binding.name.text, typesContext.runWithExpectedType(expectedType) {
                binding.rhs.accept(this)
            }))
        }

        val actualType = RecordType(fields)

        if (ExtensionsContext.hasStructuralSubtyping() && expected != null) {
            if (actualType.isApplicable(expected)) {
                return actualType
            } else {
                if (expected is RecordType) {
                    val unexpectedFields = fields.map { it.first }.subtract(expected.fields.map { it.first }.toSet())
                    if (unexpectedFields.isNotEmpty()) {
                        ErrorUnexpectedRecordFields(expected, actualType, ctx, unexpectedFields).report(parser)
                    }

                    val missingFields = expected.fields.map { it.first }.subtract(fields.map { it.first }.toSet())
                    if (missingFields.isNotEmpty()) {
                        ErrorMissingRecordFields(expected, actualType, ctx, missingFields).report(parser)
                    }
                }
                reportUnexpectedType(expected, actualType, ctx, parser)
            }
        }

        if ((expected as? RecordType)?.fields != null) {
            val unexpectedFields = fields.subtract(expected.fields.toSet())
            if (unexpectedFields.isNotEmpty()) {
                ErrorUnexpectedRecordFields(expected, actualType, ctx, unexpectedFields.map { it.first }.toSet()).report(parser)
            }

            val missingFields = expected.fields.subtract(fields.toSet())
            if (missingFields.isNotEmpty()) {
                ErrorMissingRecordFields(expected, actualType, ctx, missingFields.map { it.first }.toSet()).report(parser)
            }
        }

        return expected as? RecordType ?: actualType
    }

    override fun visitLogicAnd(ctx: LogicAndContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeApplication(ctx: TypeApplicationContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitLetRec(ctx: LetRecContext): Type {
        val variables = mutableListOf<Pair<String, Type>>()

        for (patternBinding in ctx.patternBindings) {
            val (pattern, expectedPatternType) = ExhaustivenessChecker.unwrapPattern(patternBinding.pattern(), this)

            if (expectedPatternType == null) {
                ErrorAmbiguousPatternType(patternBinding.pattern()).report(parser)
            }

            val vars = getVariablesInfoFromPattern(pattern, expectedPatternType)

            val duplicate = vars.map { it.first }.groupingBy { it }.eachCount().asIterable()
                .firstOrNull { it.value > 1 }
            if (duplicate != null) {
                ErrorDuplicatePatternVariable(patternBinding.pattern(), duplicate.key).report(parser)
            }
            variables.addAll(vars)

            val patternBindingType = typesContext.runWithExpectedType(expectedPatternType) {
                typesContext.runWithTypesInfo(variables) {
                    patternBinding.expr().accept(this)
                }
            }

            if (!patternBindingType.isApplicable(expectedPatternType)) {
                ErrorUnexpectedPatternForType(patternBindingType, pattern).report(parser)
            }

            if (!ExhaustivenessChecker.isExhaustive(listOf(pattern), patternBindingType, this)) {
                ErrorNonexhaustiveLetPatterns(patternBindingType, ctx).report(parser)
            }
        }

        return typesContext.runWithTypesInfo(variables) {
            ctx.expr().accept(this)
        }
    }

    override fun visitLogicOr(ctx: LogicOrContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTryWith(ctx: TryWithContext): Type {
        val tryType = ctx.tryExpr.accept(this)
        val fallbackType = typesContext.runWithExpectedType(tryType) { ctx.fallbackExpr.accept(this) }

        if (!fallbackType.isApplicable(tryType)) {
            reportUnexpectedType(tryType, fallbackType, ctx, parser)
        }

        return tryType
    }

    override fun visitPred(ctx: PredContext): Type {
        val argType = typesContext.runWithExpectedType(NatType) { ctx.n.accept(this) }
        if (!argType.isApplicable(NatType)) {
            reportUnexpectedType(NatType, argType, ctx, parser)
        }

        return NatType
    }

    override fun visitTypeAsc(ctx: TypeAscContext): Type {
        val expectedType = ctx.stellatype().accept(this)
        val expressionType = typesContext.runWithExpectedType(expectedType) { ctx.expr().accept(this) }

        if (!expressionType.isApplicable(expectedType)) {
            reportUnexpectedType(expectedType, expressionType, ctx, parser)
        }

        return expectedType
    }

    override fun visitNatRec(ctx: NatRecContext): Type {
        val nType = typesContext.runWithExpectedType(NatType) { ctx.n.accept(this) }
        if (!nType.isApplicable(NatType)) {
            reportUnexpectedType(NatType, nType, ctx, parser)
        }

        val zType = ctx.initial.accept(this)
        val expectedSType = FuncType(listOf(NatType), FuncType(listOf(zType), zType))
        val sType = typesContext.runWithExpectedType(expectedSType) { ctx.step.accept(this) }

        if (!sType.isApplicable(expectedSType)) {
            reportUnexpectedType(expectedSType, sType, ctx, parser)
        }

        return zType
    }

    override fun visitUnfold(ctx: UnfoldContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitRef(ctx: RefContext): Type {
        val nestedType = typesContext.runWithExpectedType((typesContext.getExpectedType() as? RefType)?.nestedType) {
            ctx.expr().accept(this)
        }
        return RefType(nestedType)
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
        val expressionExpectedType = typesContext.getExpectedType()?.let { FuncType(listOf(it), it) }
        val expressionType = typesContext.runWithExpectedType(expressionExpectedType) { ctx.expr().accept(this) }
        if (expressionType !is FuncType || expressionType.argTypes.size != 1) {
            ErrorNotAFunction(ctx.expr(), expressionType).report(parser)
        }

        val expectedType = FuncType(expressionType.argTypes, expressionType.argTypes.first())
        if (!expressionType.isApplicable(expectedType)) {
            reportUnexpectedType(expectedType, expressionType, ctx, parser)
        }

        return expectedType.argTypes.first()
    }

    override fun visitLet(ctx: LetContext): Type {
        val variables = mutableListOf<Pair<String, Type>>()

        for (patternBinding in ctx.patternBindings) {
            val (pattern, expectedPatternType) = ExhaustivenessChecker.unwrapPattern(patternBinding.pattern(), this)

            val patternBindingType = typesContext.runWithExpectedType(expectedPatternType) {
                typesContext.runWithTypesInfo(variables) {
                    patternBinding.expr().accept(this)
                }
            }

            if (expectedPatternType != null && !patternBindingType.isApplicable(expectedPatternType)) {
                ErrorUnexpectedPatternForType(expectedPatternType, pattern).report(parser)
            }

            if (!ExhaustivenessChecker.isExhaustive(listOf(pattern), patternBindingType, this)) {
                ErrorNonexhaustiveLetPatterns(patternBindingType, ctx).report(parser)
            }

            val vars = getVariablesInfoFromPattern(pattern, patternBindingType)

            val duplicate = vars.map { it.first }.groupingBy { it }.eachCount().asIterable()
                .firstOrNull { it.value > 1 }
            if (duplicate != null) {
                ErrorDuplicatePatternVariable(pattern, duplicate.key).report(parser)
            }

            variables.addAll(vars)
        }

        return typesContext.runWithTypesInfo(variables) {
            ctx.expr().accept(this)
        }
    }

    override fun visitAssign(ctx: AssignContext): Type {
        val leftType = typesContext.runWithExpectedType(null) { ctx.lhs.accept(this) }
        if (leftType !is RefType) {
            ErrorNotAReference(ctx).report(parser)
        }

        val rightType = typesContext.runWithExpectedType(leftType.nestedType) { ctx.rhs.accept(this) }
        if (!rightType.isApplicable(leftType.nestedType)) {
            reportUnexpectedType(leftType.nestedType, rightType, ctx, parser)
        }

        return UnitType
    }

    override fun visitTuple(ctx: TupleContext): Type {
        val expected = typesContext.getExpectedType()
        if (expected != null && expected !is TupleType) {
            ErrorUnexpectedTuple(expected, ctx).report(parser)
        }

        if (expected is TupleType && expected.types.size != ctx.expr().size) {
            ErrorUnexpectedTupleLength(expected, ctx).report(parser)
        }

        val expressionTypes = mutableListOf<Type>()
        for (i in ctx.exprs.indices) {
            expressionTypes.add(typesContext.runWithExpectedType((expected as? TupleType)?.types?.get(i)) {
                ctx.exprs[i].accept(this)
            })
        }
        return TupleType(expressionTypes)
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
        if (!tailType.isApplicable(expectedListType)) {
            reportUnexpectedType(expectedListType, tailType, ctx, parser)
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
        TODO("Not yet implemented")
    }

    override fun visitPatternVariant(ctx: PatternVariantContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitPatternAsc(ctx: PatternAscContext?): Type {
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

    override fun visitPatternCastAs(ctx: PatternCastAsContext): Type {
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
        return Top
    }

    override fun visitTypeBool(ctx: TypeBoolContext): Type {
        return BoolType
    }

    override fun visitTypeRef(ctx: TypeRefContext): Type {
        return RefType(ctx.stellatype().accept(this))
    }

    override fun visitTypeRec(ctx: TypeRecContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeAuto(ctx: TypeAutoContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeSum(ctx: TypeSumContext): Type {
        return SumType(ctx.left.accept(this), ctx.right.accept(this))
    }

    override fun visitTypeVar(ctx: TypeVarContext): Type {
        TODO("Not yet implemented")
    }

    override fun visitTypeVariant(ctx: TypeVariantContext): Type {
        return VariantType(ctx.fieldTypes.map { Pair(it.label.text, it.stellatype()?.accept(this)) })
    }

    override fun visitTypeUnit(ctx: TypeUnitContext): Type {
        return UnitType
    }

    override fun visitTypeNat(ctx: TypeNatContext): Type {
        return NatType
    }

    override fun visitTypeBottom(ctx: TypeBottomContext): Type {
        return Bot
    }

    override fun visitTypeParens(ctx: TypeParensContext): Type {
        return ctx.stellatype().accept(this)
    }

    override fun visitTypeFun(ctx: TypeFunContext): Type {
        val paramType = ctx.paramTypes.map { it.accept(this) }
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