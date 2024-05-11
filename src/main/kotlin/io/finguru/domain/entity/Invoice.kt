package io.finguru.domain.entity

import io.finguru.application.response.InvoiceResponse
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Invoice(
    @BsonId val id: ObjectId,
    val type: String,
    val invoiceDate: String,
    val dueDate: String,
    val invoiceNo: String,
    val partnerId: String,
    val creditTerm: String,
    val reference: String,
    val invoiceTotal: String,
    val invoiceItems: List<InvoiceItem>,
) {
    fun toResponse() = InvoiceResponse(
        id = id.toString()
    )
}

data class InvoiceItem(
    val productName: String,
    val quantity: String,
    val rate: String,
    val valueOfSupplies: String,
    val salesTax: String,
    val netAmount: String,
)

data class InvoiceJson(
    val partner: Partner?,
    val invoice: Invoice
)