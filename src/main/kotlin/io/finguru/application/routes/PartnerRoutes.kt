package io.finguru.application.routes

import io.finguru.application.request.PartnerRequest
import io.finguru.domain.ports.PartnerRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bson.types.ObjectId
import org.koin.ktor.ext.inject

fun Route.partnerRoutes() {
    val repository by inject<PartnerRepository>()
    route("/partners") {
        post {
            try {
                val partner = call.receive<PartnerRequest>()
                val res = repository.create(partner.toDomain())
                if (res != null) {
                    call.respond(
                        HttpStatusCode.Created,
                        "${partner.type.replaceFirstChar { it.uppercase() }} added successfully: ${partner.firstName}  ${partner.lastName}."
                    )
                } else {
                    call.respond(
                        HttpStatusCode.InternalServerError, "Failed to add partner to database"
                    )
                }
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest, "Invalid input content")
            }
        }

        delete("/{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(
                HttpStatusCode.BadRequest, mapOf(
                    "msg" to "Missing partner id",
                    "status" to HttpStatusCode.BadRequest
                )
            )
            val res = repository.delete(ObjectId(id))
            if (res == 1L) {
                return@delete call.respond(HttpStatusCode.OK, "Successfully deleted $id")
            }
            return@delete call.respond(HttpStatusCode.NoContent, "Partner deletion failed")
        }
    }
}