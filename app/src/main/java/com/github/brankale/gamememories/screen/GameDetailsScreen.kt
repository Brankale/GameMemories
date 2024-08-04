package com.github.brankale.gamememories.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.github.brankale.common.data.repository.IGDBRepository
import com.github.brankale.common.domain.model.Filter
import kotlinx.coroutines.runBlocking

@Composable
fun GameDetailsScreen() {

}

fun main() {
//    runBlocking {
//        val cover = IGDBRepository().getCovers(
//            Filter.Builder()
////                .addField(Covers::url.serialName())
//                .build()
//        )
//        println(cover)
//    }
}

@Preview
@Composable
private fun GameDetailsScreenPreview() {
    GameDetailsScreen()
}