package io.finguru.domain.ports

import io.finguru.domain.entity.Partner
import org.bson.BsonValue
import org.bson.types.ObjectId


interface PartnerRepository {
    suspend fun findAll(): List<Partner>
    suspend fun findById(id: ObjectId): Partner?
    suspend fun create(entity: Partner): BsonValue?
    suspend fun update(entity: Partner): Long
    suspend fun delete(id: ObjectId): Long
}