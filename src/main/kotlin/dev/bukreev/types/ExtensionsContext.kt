package dev.bukreev.types

object ExtensionsContext {

    private val extensions = mutableSetOf<String>()

    fun addExtensions(extensions: List<String>) {
        this.extensions.addAll(extensions)
    }

    fun hasStructuralSubtyping() = extensions.contains("#structural-subtyping")

    fun hasAmbiguousTypeAsBottom() = extensions.contains("#ambiguous-type-as-bottom")

    fun hasTypeReconstruction() = extensions.contains("#type-reconstruction")

    fun reset() {
        extensions.clear()
    }
}