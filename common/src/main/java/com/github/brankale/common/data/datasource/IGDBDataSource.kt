package com.github.brankale.common.data.datasource

import com.github.brankale.common.data.di.IGDBAuthHttpClient
import com.github.brankale.common.data.model.Covers
import com.github.brankale.common.domain.model.Filter
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class IGDBDataSource @Inject constructor(
    @IGDBAuthHttpClient private val httpClient: HttpClient
) {

    suspend fun getCovers(filter: Filter = Filter.DEFAULT): List<Covers>  {
        return httpClient.post("/v4/covers") {
            setBody(filter.toString())
        }.body()
    }

}