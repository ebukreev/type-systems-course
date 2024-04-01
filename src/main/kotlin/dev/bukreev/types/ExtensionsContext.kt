package dev.bukreev.types

class ExtensionsContext {

    private val extensions = mutableSetOf<String>()

    fun addExtensions(extensions: List<String>) {
        this.extensions.addAll(extensions)
    }

    fun hasStructuralSubtyping() = extensions.contains("#structural-subtyping")
}