package com.github.brankale.gamememories.utils

import kotlinx.serialization.SerialName
import kotlin.reflect.KProperty

fun KProperty<*>.serialName() =
    (annotations.find { it is SerialName } as SerialName).value