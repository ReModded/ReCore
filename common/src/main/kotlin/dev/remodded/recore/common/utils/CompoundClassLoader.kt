package dev.remodded.recore.common.utils

class CompoundClassLoader : ClassLoader() {
    private val classLoaders = hashSetOf<ClassLoader>()

    override fun loadClass(name: String): Class<*> {
        for (classLoader in classLoaders) {
            try {
                return classLoader.loadClass(name)
            } catch (_: ClassNotFoundException) {
                continue
            }
        }
        throw ClassNotFoundException(name)
    }

    fun addClassLoader(classLoader: ClassLoader) {
        classLoaders.add(classLoader)
    }
}
