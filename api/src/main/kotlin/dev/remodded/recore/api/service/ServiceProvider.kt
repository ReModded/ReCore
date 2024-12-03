package dev.remodded.recore.api.service

import dev.remodded.recore.api.plugins.ReCorePlugin
import dev.remodded.recore.api.utils.Debuggable
import java.util.function.Supplier

interface ServiceProvider : Debuggable {
    fun <T> provide(service: Class<T>, plugin: ReCorePlugin, priority: ServicePriority = ServicePriority.Default, provider: Supplier<T>): Boolean
    fun <T, K: T> provide(service: Class<T>, serviceImpl: Class<K>, plugin: ReCorePlugin, priority: ServicePriority = ServicePriority.Default): Boolean

    fun <T> getService(service: Class<T>): T
    fun <T> getLazyService(service: Class<T>): Lazy<T>
}

inline fun <reified T> ServiceProvider.provide(plugin: ReCorePlugin, priority: ServicePriority = ServicePriority.Default, provider: Supplier<T>) = provide(T::class.java, plugin, priority, provider)
inline fun <reified T, reified K : T> ServiceProvider.provide(plugin: ReCorePlugin, priority: ServicePriority = ServicePriority.Default) = provide(T::class.java, K::class.java, plugin, priority)

inline fun <reified T> ServiceProvider.getService() = getService(T::class.java)
inline fun <reified T> ServiceProvider.getLazyService() = getLazyService(T::class.java)
