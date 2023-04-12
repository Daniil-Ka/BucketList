package com.tms.bucketlist.domain

data class Category(
    val id: Long,
    val name: String,
    val description: String,
    val photoUrl: String,
) {
    companion object {
        val DefaultCategory = Category(
            0,
            "Default",
            "Description",
            "URL"
        )
    }
}
