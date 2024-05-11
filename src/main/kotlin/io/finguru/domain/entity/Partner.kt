package io.finguru.domain.entity

import io.finguru.application.response.PartnerResponse
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Partner(
    @BsonId val id: ObjectId,
    val type: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val status: String,
    val contactNo: String,
    val openingBalance: String,
    val address: String,
    val repName: String,
    val repContact: String,
    val repDesignation: String
) {
    fun toResponse() = PartnerResponse(
        id = id.toString(),
        type = type,
        firstName = firstName,
        lastName = lastName,
        email = email,
        status = status,
        contactNo = contactNo,
        openingBalance = openingBalance,
        address = address,
        repName = repName,
        repContact = repContact,
        repDesignation = repDesignation
    )
}