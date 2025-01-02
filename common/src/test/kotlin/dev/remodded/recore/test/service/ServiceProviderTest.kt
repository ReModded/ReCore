package dev.remodded.recore.test.service

import dev.remodded.recore.api.service.ServiceProvider
import dev.remodded.recore.api.service.getService
import dev.remodded.recore.api.service.provide
import dev.remodded.recore.common.service.CommonServiceProvider
import dev.remodded.recore.test.ReCoreTest
import org.junit.Test
import kotlin.test.BeforeTest
import kotlin.test.assertFails

class ServiceProviderTest {

    val plugin = ReCoreTest()
    lateinit var provider: ServiceProvider

    interface Service

    interface Service2ndLayer : Service

    class Service1 : Service
    class Service2 : Service2ndLayer


    @BeforeTest
    fun init() {
        provider = CommonServiceProvider()
    }


    @Test
    fun fallbackProvide() {
        assertFails { provider.getService<Service>() }

        provider.provide<Service, Service2>(plugin)

        provider.getService<Service>()

        provider.getService<Service2ndLayer>()
    }
}
