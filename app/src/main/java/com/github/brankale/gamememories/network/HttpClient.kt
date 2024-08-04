package com.github.brankale.gamememories.network

import com.github.brankale.gamememories.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private val IGDB_HOST = "api.igdb.com"
private val BASE_URL = "https://$IGDB_HOST/v4"

@Serializable
private data class TwitchAuthResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("expires_in") val expiresIn: Long,
    @SerialName("token_type") val tokenType: String,
)

suspend fun main() {
    val client = HttpClient(CIO) {
//        install(Logging)
        install(Auth) {
            bearer {
                loadTokens {
                    val response = refreshToken()
                    println("load tokens")
                    BearerTokens(response.accessToken, "")
                }
                refreshTokens {
                    val response = refreshToken()
                    println("refresh tokens")
                    BearerTokens(response.accessToken, "")
                }
            }
        }
    }

    val response = client.post("$BASE_URL/games") {
        header("Client-ID", BuildConfig.CLIENT_ID)
    }

    println(response.bodyAsText())
}

private suspend fun refreshToken(): TwitchAuthResponse {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    return client.post("https://id.twitch.tv/oauth2/token") {
        url {
            parameters.append("client_id", BuildConfig.CLIENT_ID)
            parameters.append("client_secret", BuildConfig.CLIENT_SECRET)
            parameters.append("grant_type", "client_credentials")
        }
    }.body()
}