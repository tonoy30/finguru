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
        get {
            val partners = repository.findAll().map { it.toResponse() }
            call.respond(HttpStatusCode.OK, partners)
        }

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
                        HttpStatusCode.InternalServerError, "failed to add partner to database"
                    )
                }
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest, "invalid input content")
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(
                HttpStatusCode.BadRequest, mapOf(
                    "msg" to "Missing partner id",
                    "status" to HttpStatusCode.BadRequest
                )
            )
            val res = repository.delete(ObjectId(id))
            if (res == 1L) {
                return@delete call.respond(HttpStatusCode.OK, "successfully deleted $id")
            }
            return@delete call.respond(HttpStatusCode.NoContent, "partner deletion failed")
        }

        patch("/{id}") {
            val id = call.parameters["id"] ?: return@patch call.respond(
                HttpStatusCode.BadRequest, mapOf(
                    "msg" to "Missing partner id",
                    "status" to HttpStatusCode.BadRequest
                )
            )
            val updated = repository.update(ObjectId(id), call.receive())
            call.respond(
                if (updated == 1L) HttpStatusCode.OK else HttpStatusCode.NotFound,
                if (updated == 1L) "partner updated successfully" else "partner not found"
            )
        }

        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(
                HttpStatusCode.BadRequest, mapOf(
                    "msg" to "Missing partner id",
                    "status" to HttpStatusCode.BadRequest
                )
            )
            repository.findById(ObjectId(id))?.let {
                call.respond(HttpStatusCode.OK, it.toResponse())
            } ?: call.respond(HttpStatusCode.NotFound, "no records found for id $id")

        }
    }
}