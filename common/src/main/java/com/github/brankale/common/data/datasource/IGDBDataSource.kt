package com.github.brankale.common.data.datasource

import com.github.brankale.common.data.igdbHttpClient
import com.github.brankale.common.data.model.Covers
import com.github.brankale.common.domain.model.Filter
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class IGDBDataSource {

    suspend fun getCovers(filter: Filter = Filter.DEFAULT): List<Covers>  {
        return igdbHttpClient.post("/v4/covers") {
            setBody(filter.toString())
        }.body()
    }

}