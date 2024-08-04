package com.github.brankale.common.data

import android.content.Context
import com.github.brankale.common.BuildConfig
import com.github.brankale.common.data.datastore.twitchAuthTokenDataStore
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

private val IGDB_HOST = "api.igdb.com"

@Serializable
private data class TwitchAuthResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("expires_in") val expiresIn: Long,
    @SerialName("token_type") val tokenType: String,
)

fun getIGDBHttpClient(context: Context): HttpClient {
    return HttpClient(CIO) {
        install(Logging)
        install(DefaultRequest) {
            url {
                protocol = URLProtocol.HTTPS
                host = IGDB_HOST
            }
            header("Client-ID", BuildConfig.CLIENT_ID)
        }
        install(Auth) {
            bearer {
                loadTokens {
                    val response = refreshIGDBToken(context)
                    println("load tokens")
                    BearerTokens(response.accessToken, "")
                }
                refreshTokens {
                    val response = refreshIGDBToken(context)
                    println("refresh tokens")
                    BearerTokens(response.accessToken, "")
                }
            }
        }
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }
}

private suspend fun refreshIGDBToken(context: Context): TwitchAuthResponse {
    val auth = context.twitchAuthTokenDataStore.data.first()

    if (!auth.accessToken.isNullOrBlank())
        return TwitchAuthResponse(
            accessToken = auth.accessToken,
            expiresIn = auth.expiresIn,
            tokenType = auth.tokenType
        )

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    val response = client.post("https://id.twitch.tv/oauth2/token") {
        url {
            parameters.append("client_id", BuildConfig.CLIENT_ID)
            parameters.append("client_secret", BuildConfig.CLIENT_SECRET)
            parameters.append("grant_type", "client_credentials")
        }
    }

    val twitchAuthResponse: TwitchAuthResponse = response.body()

    context.twitchAuthTokenDataStore.updateData { currentTwitchAuthToken ->
        currentTwitchAuthToken.toBuilder()
            .setAccessToken(twitchAuthResponse.accessToken)
            .setExpiresIn(twitchAuthResponse.expiresIn)
            .setTokenType(twitchAuthResponse.tokenType)
            .build()
    }

    return twitchAuthResponse
}