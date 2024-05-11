package io.finguru.plugins

import com.mongodb.kotlin.client.coroutine.MongoClient
import io.finguru.domain.ports.PartnerRepository
import io.finguru.infrastructure.PartnerRepositoryImpl
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger


fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(module {
            single {
                MongoClient.create(
                    environment.config.propertyOrNull("ktor.mongo.uri")?.getString()
                        ?: throw RuntimeException("Failed to access MongoDB URI.")
                )
            }
            single {
                get<MongoClient>().getDatabase(environment.config.property("ktor.mongo.database").getString())
            }
        }, module {
            single<PartnerRepository> { PartnerRepositoryImpl(get()) }
        })
    }
}
