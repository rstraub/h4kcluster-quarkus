package com.ubertob.h4kcluster.adapter.bootstrap

import org.http4k.core.HttpHandler

interface ApplicationId {
  val hostname: String
}

data class Application(
      val id: ApplicationId,
      val description: String,
      val handler: HttpHandler
)