package dev.remodded.recore.common.service

import dev.remodded.recore.api.service.ServicePriority
import dev.remodded.recore.api.service.ServiceProvider
import java.util.function.Supplier

class CommonServiceProvider : ServiceProvider {

    private class RegisteredService<T>(
        val provider: Supplier<T>,
        val priority: ServicePriority,
    ) {
        var cachedService: T? = null
    }

    private val services: MutableMap<Class<*>, RegisteredService<*>> = hashMapOf()

    override fun <T> registerService(service: Class<T>, priority: ServicePriority, provider: Supplier<T>): Boolean {
        val registeredService = services[service]

        if (registeredService != null && registeredService.priority >= priority)
            return false

        services[service] = RegisteredService(provider, priority)
        return true
    }

    override fun <T, K: T> registerService(service: Class<T>, serviceImpl: Class<K>, priority: ServicePriority) =
        registerService(service, priority, serviceImpl.getDeclaredConstructor()::newInstance)

    override fun <T> getService(service: Class<T>): T {
        val srv = services[service]

        if (srv == null) throw IllegalStateException("Service not found")

        @Suppress("UNCHECKED_CAST")
        srv as RegisteredService<T>

        if (srv.cachedService == null) {
            srv.cachedService = srv.provider.get()
        }

        return srv.cachedService!!
    }

    override fun <T> getLazyService(service: Class<T>): Lazy<T> {
        return lazy { getService(service) }
    }
}
