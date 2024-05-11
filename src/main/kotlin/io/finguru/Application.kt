package io.finguru

import io.finguru.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureKoin()
    configureHTTP()
    configureRouting()
}
