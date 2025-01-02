package dev.remodded.recore.common.service

import dev.remodded.recore.api.plugins.ReCorePlugin
import dev.remodded.recore.api.service.ServicePriority
import dev.remodded.recore.api.service.ServiceProvider
import java.util.function.Supplier

class CommonServiceProvider : ServiceProvider {

    private class RegisteredService<T>(
        val provider: Supplier<T>,
        val priority: ServicePriority,
        val plugin: ReCorePlugin,
    ) {
        var cachedService: T? = null
    }

    private val services: MutableMap<Class<*>, RegisteredService<*>> = hashMapOf()

    override fun <T> provide(service: Class<T>, plugin: ReCorePlugin, priority: ServicePriority, provider: Supplier<T>): Boolean {
        val registeredService = services[service]

        if (registeredService != null && registeredService.priority >= priority)
            return false

        services[service] = RegisteredService(provider, priority, plugin)
        return true
    }

    override fun <T, K: T> provide(service: Class<T>, serviceImpl: Class<K>, plugin: ReCorePlugin, priority: ServicePriority): Boolean {
        return provide(service, plugin, priority, serviceImpl.getDeclaredConstructor()::newInstance)
//        return registerService(service, plugin, priority) {
//            var defaultCtor: Supplier<T>? = null
//
//            serviceImpl.declaredConstructors.forEach { ctor ->
//                if (ctor.parameterCount == 0) {
//                    @Suppress("UNCHECKED_CAST")
//                    defaultCtor = ctor::newInstance as Supplier<T>
//                    return@forEach
//                }
//
//                try {
//                    val params = ctor.parameterTypes.map { param ->
//                        getService(param)
//                    }.toTypedArray()
//
//                    return@registerService ctor.newInstance(*params) as T
//                } catch (_: Exception) {}
//            }
//
//            if (defaultCtor != null) {
//                return@registerService defaultCtor.get()
//            }
//            throw IllegalStateException("No suitable constructor found")
//        }
    }

    override fun <T> getService(service: Class<T>): T {
        var srv = services[service]

        if (srv == null) {
            srv = services.entries.find {
                it.key.isAssignableFrom(service)
            }?.value ?: throw IllegalStateException("Service not found")
        }

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

    override fun getDebugInfo(): String {
        return "ReCore Services:\n" + services.map { (service, srv) ->
            "  ${service.name} -> ${srv.plugin.getPluginInfo().name}" + if (srv.cachedService != null) " (${srv.cachedService!!.javaClass.name})" else ""
        }.joinToString("\n")
    }
}
