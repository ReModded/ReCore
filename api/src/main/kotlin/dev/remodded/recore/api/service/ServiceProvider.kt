package dev.remodded.recore.api.service

import java.util.function.Supplier

interface ServiceProvider {
    fun <T> registerService(service: Class<T>, priority: ServicePriority = ServicePriority.Default, provider: Supplier<T>): Boolean
    fun <T, K: T> registerService(service: Class<T>, serviceImpl: Class<K>, priority: ServicePriority = ServicePriority.Default): Boolean

    fun <T> getService(service: Class<T>): T
     fun <T> getLazyService(service: Class<T>): Lazy<T>
}

inline fun <reified T> ServiceProvider.registerService(priority: ServicePriority = ServicePriority.Default, provider: Supplier<T>) = registerService(T::class.java, priority, provider)
inline fun <reified T, reified K : T> ServiceProvider.registerService(priority: ServicePriority = ServicePriority.Default) = registerService(T::class.java, K::class.java, priority)

inline fun <reified T> ServiceProvider.getService() = getService(T::class.java)
inline fun <reified T> ServiceProvider.getLazyService() = getLazyService(T::class.java)
