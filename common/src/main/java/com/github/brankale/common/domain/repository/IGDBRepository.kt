package com.github.brankale.common.domain.repository

import com.github.brankale.common.data.model.Covers
import com.github.brankale.common.domain.model.Filter


interface IGDBRepository {

    suspend fun getCovers(filter: Filter = Filter.DEFAULT): List<Covers>

}