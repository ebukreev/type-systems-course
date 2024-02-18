package dev.bukreev.types

sealed interface Error {
    fun stringify(): String
    
    fun report(): Nothing {
        TODO()
    }
}

data object ErrorMissingMain : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorUndefinedVariable : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorUnexpectedTypeForExpression : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorNotAFunction : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorNotATuple : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorNotARecord : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorNotAList : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorUnexpectedLambda : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorUnexpectedTypeForParameter : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorUnexpectedTuple : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorUnexpectedRecord : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorUnexpectedList : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorUnexpectedInjection : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorMissingRecordFields : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorUnexpectedRecordFields : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorUnexpectedFieldAccess : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorTupleIndexOfBounds : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorUnexpectedTupleLength : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorAmbiguousSumType : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorAmbiguousList : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorIllegalEmptyMatching : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorNonexhaustiveMatchPatterns : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}

data object ErrorUnexpectedPatternForType : Error {
    override fun stringify(): String {
        TODO("Not yet implemented")
    }
}