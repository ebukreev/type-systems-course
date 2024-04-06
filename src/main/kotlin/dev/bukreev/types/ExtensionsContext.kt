package dev.bukreev.types

object ExtensionsContext {

    private val extensions = mutableSetOf<String>()

    fun addExtensions(extensions: List<String>) {
        this.extensions.addAll(extensions)
    }

    fun hasStructuralSubtyping() = extensions.contains("#structural-subtyping")

    fun reset() {
        extensions.clear()
    }
}