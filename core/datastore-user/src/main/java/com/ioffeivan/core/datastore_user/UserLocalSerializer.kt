package com.ioffeivan.core.datastore_user

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.ioffeivan.core.datastore_user.model.UserLocal
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

internal object UserLocalSerializer : Serializer<UserLocal> {
    override val defaultValue: UserLocal = UserLocal()

    override suspend fun readFrom(input: InputStream): UserLocal {
        return try {
            Json.decodeFromString<UserLocal>(
                input.readBytes().decodeToString(),
            )
        } catch (e: SerializationException) {
            throw CorruptionException("Unable to read UserLocal", e)
        }
    }

    override suspend fun writeTo(t: UserLocal, output: OutputStream) {
        output.write(
            Json.encodeToString(t)
                .encodeToByteArray(),
        )
    }
}
