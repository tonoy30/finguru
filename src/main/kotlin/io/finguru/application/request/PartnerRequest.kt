package io.finguru.application.request

import io.finguru.domain.entity.Partner
import org.bson.types.ObjectId

data class PartnerRequest(
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
    fun toDomain() = Partner(
        id = ObjectId(),
        firstName = firstName,
        lastName = lastName,
        email = email,
        status = status,
        contactNo = contactNo,
        openingBalance = openingBalance,
        address = address,
        repName = repName,
        repContact = repContact,
        repDesignation = repDesignation,
        type = type
    )
}