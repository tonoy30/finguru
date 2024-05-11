package io.finguru.infrastructure

import com.mongodb.MongoException
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.finguru.domain.entity.Partner
import io.finguru.domain.ports.PartnerRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import org.bson.BsonValue
import org.bson.types.ObjectId

class PartnerRepositoryImpl(private val mongo: MongoDatabase) : PartnerRepository {
    companion object {
        const val PARTNER_COLLECTION = "partners"
    }

    override suspend fun findAll(): List<Partner> {
        try {
            val res = mongo.getCollection<Partner>(PARTNER_COLLECTION)
                .find()
                .toList()
            return res
        } catch (e: MongoException) {
            return emptyList()
        }
    }

    override suspend fun findById(id: ObjectId): Partner? {
        val filter = Filters.eq("_id", id)
        val res = mongo.getCollection<Partner>(PARTNER_COLLECTION)
            .withDocumentClass<Partner>()
            .find(filter)
            .firstOrNull()
        return res
    }

    override suspend fun create(entity: Partner): BsonValue? {
        try {
            val res = mongo.getCollection<Partner>(PARTNER_COLLECTION).insertOne(entity)
            return res.insertedId
        } catch (e: MongoException) {
            println("[REPO] unable to create partner due to: $e")
        }
        return null
    }

    override suspend fun update(id: ObjectId, entity: Partner): Long {
        try {
            val filter = Filters.eq("_id", id)
            val updates = Updates.combine(
                Updates.set(Partner::type.name, entity.type),
                Updates.set(Partner::firstName.name, entity.firstName),
                Updates.set(Partner::lastName.name, entity.lastName),
                Updates.set(Partner::email.name, entity.email),
                Updates.set(Partner::status.name, entity.status),
                Updates.set(Partner::contactNo.name, entity.contactNo),
                Updates.set(Partner::openingBalance.name, entity.openingBalance),
                Updates.set(Partner::address.name, entity.address),
                Updates.set(Partner::repName.name, entity.repName),
                Updates.set(Partner::repContact.name, entity.repContact),
                Updates.set(Partner::repDesignation.name, entity.repDesignation),
            )
            val options = UpdateOptions().upsert(true)
            val res = mongo.getCollection<Partner>(PARTNER_COLLECTION)
                .updateOne(filter, updates, options)
            return res.modifiedCount
        } catch (e: MongoException) {
            println("[REPO] unable to update partner due to: $e")
        }
        return 0
    }

    override suspend fun delete(id: ObjectId): Long {
        try {
            val filter = Filters.eq("_id", id)
            val res = mongo.getCollection<Partner>(PARTNER_COLLECTION)
                .deleteOne(filter)
            return res.deletedCount
        } catch (e: MongoException) {
            println("[REPO] unable to delete partner due to: $e")
        }
        return 0
    }
}