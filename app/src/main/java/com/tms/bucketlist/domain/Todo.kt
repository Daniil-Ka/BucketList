package com.tms.bucketlist.domain

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Todo (
    val name: String,
    val description: String,
    var isCompeted: Boolean
)
