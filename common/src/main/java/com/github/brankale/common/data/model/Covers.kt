package com.github.brankale.common.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Covers(
    @SerialName("alpha_channel") val alphaChannel: Boolean? = null,
    @SerialName("animated") val animated: Boolean? = null,
//    @SerialName("checksum") val checksum: String,
//    @SerialName("game") val game: String,
//    @SerialName("game_localization") val gameLocalization: String,
    @SerialName("height") val height: Int? = null,
    @SerialName("image_id") val imageId: String? = null,
    @SerialName("url") val url: String? = null,
    @SerialName("width") val width: Int? = null,
)