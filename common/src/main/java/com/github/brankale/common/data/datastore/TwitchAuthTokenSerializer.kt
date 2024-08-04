package com.github.brankale.common.data.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.github.brankale.common.proto.TwitchAuthToken
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream


private object TwitchAuthTokenSerializer: Serializer<TwitchAuthToken> {

    override val defaultValue: TwitchAuthToken
        get() = TwitchAuthToken.getDefaultInstance()


    override suspend fun readFrom(input: InputStream): TwitchAuthToken {
        try {
            return TwitchAuthToken.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: TwitchAuthToken, output: OutputStream) {
        t.writeTo(output)
    }

}

val Context.twitchAuthTokenDataStore: DataStore<TwitchAuthToken> by dataStore(
    fileName = "twitchAuthToken.pb",
    serializer = TwitchAuthTokenSerializer
)