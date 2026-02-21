package com.ioffeivan.core.datastore_auth

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.ioffeivan.core.datastore_auth.model.AuthDataLocal
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import java.util.Base64

internal object AuthDataLocalSerializer : Serializer<AuthDataLocal> {
    override val defaultValue: AuthDataLocal = AuthDataLocal()

    override suspend fun readFrom(input: InputStream): AuthDataLocal {
        return try {
            val encryptedBytes = input.use { it.readBytes() }
            val encryptedBytesDecoded = Base64.getDecoder().decode(encryptedBytes)
            val decryptedBytes = Encryptor.decrypt(encryptedBytesDecoded)
            val decodedJsonString = decryptedBytes.decodeToString()

            Json.decodeFromString(decodedJsonString)
        } catch (e: Exception) {
            throw CorruptionException("Unable to read AuthDataLocal", e)
        }
    }

    override suspend fun writeTo(t: AuthDataLocal, output: OutputStream) {
        val json = Json.encodeToString(t)
        val bytes = json.toByteArray()
        val encryptedBytes = Encryptor.encrypt(bytes)
        val encryptedBytesBase64 = Base64.getEncoder().encode(encryptedBytes)

        output.use {
            it.write(encryptedBytesBase64)
        }
    }
}
