package com.github.brankale.common.data.repository

import com.github.brankale.common.data.datasource.IGDBDataSource
import com.github.brankale.common.data.model.Covers
import com.github.brankale.common.domain.model.Filter
import com.github.brankale.common.domain.repository.IGDBRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IGDBRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val datasource: IGDBDataSource
): IGDBRepository {

    override suspend fun getCovers(filter: Filter): List<Covers>  {
        return withContext(dispatcher) {
            return@withContext datasource.getCovers(filter)
        }
    }

}