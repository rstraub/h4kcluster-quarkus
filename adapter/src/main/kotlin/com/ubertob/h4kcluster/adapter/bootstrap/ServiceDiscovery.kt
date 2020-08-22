package com.ubertob.h4kcluster.adapter.bootstrap

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import java.util.concurrent.atomic.AtomicReference

interface ServiceDiscovery {
    fun provideHttpClient(id: ApplicationId): HttpHandler?
    fun register(creator: (ServiceDiscovery) -> Application)
}

class InProcessServiceDiscovery : ServiceDiscovery {
    private val applications: AtomicReference< Map<ApplicationId, Application>> = AtomicReference( emptyMap())

    override fun provideHttpClient(id: ApplicationId): HttpHandler {
        return applications.get()[id]?.handler ?: {Response(Status.BAD_GATEWAY).body("application unknown $id")}
    }

    override fun register(creator: (ServiceDiscovery) -> Application) {
        applications.getAndUpdate { it + creator(this).toMapEntry()  }
    }

    fun findByHostname(hostname: String): Application? =
        applications.get().values.firstOrNull { it.id.hostname == hostname }

}

private fun Application.toMapEntry(): Pair<ApplicationId, Application> =
        this.id to this


class DeployablesServiceDiscover : ServiceDiscovery {
    override fun provideHttpClient(id: ApplicationId): HttpHandler {
        TODO("Not yet implemented")
        //      return HttpOkClient().setBaseUri(calculateUri(id))
    }

    override fun register(creator: (ServiceDiscovery) -> Application) {
        TODO("Not yet implemented")
    }

}