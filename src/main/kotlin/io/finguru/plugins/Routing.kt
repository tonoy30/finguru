package io.finguru.plugins

import io.finguru.application.routes.partnerRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        partnerRoutes()
    }
}
