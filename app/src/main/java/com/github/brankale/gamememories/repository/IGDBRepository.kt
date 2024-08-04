package com.github.brankale.gamememories.repository

import com.github.brankale.gamememories.model.Covers
import com.github.brankale.gamememories.network.igdbHttpClient
import com.github.brankale.gamememories.utils.Filter
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IGDBRepository(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getCovers(filter: Filter = Filter.DEFAULT): List<Covers>  {
        return withContext(dispatcher) {
            return@withContext igdbHttpClient.post("/v4/covers") {
                setBody(filter.toString())
            }.body()
        }
    }

}