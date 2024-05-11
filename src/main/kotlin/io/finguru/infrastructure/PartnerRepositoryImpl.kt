package io.finguru.infrastructure

import com.mongodb.MongoException
import com.mongodb.MongoWriteException
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.finguru.domain.entity.Partner
import io.finguru.domain.ports.PartnerRepository
import org.bson.BsonValue
import org.bson.types.ObjectId

class PartnerRepositoryImpl(private val mongo: MongoDatabase) : PartnerRepository {
    companion object {
        const val PARTNER_COLLECTION = "partners"
    }

    override suspend fun findAll(): List<Partner> {
        TODO("Not yet implemented")
    }

    override suspend fun findById(id: ObjectId): Partner? {
        TODO("Not yet implemented")
    }

    override suspend fun create(entity: Partner): BsonValue? {
        try {
            val res = mongo.getCollection<Partner>(PARTNER_COLLECTION).insertOne(entity)
            return res.insertedId
        } catch (e: MongoException) {
            println("unable to create partner: $e")
        }
        return null
    }

    override suspend fun update(entity: Partner): Long {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: ObjectId): Long {
        TODO("Not yet implemented")
    }
}