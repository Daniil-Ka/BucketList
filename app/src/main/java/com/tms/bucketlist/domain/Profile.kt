package com.tms.bucketlist.domain

/** Профиль пользователя */
data class Profile(
    val id: Long,
    val name: String,
    val description: String,
    val photoUrl: String,
    val isFavorite: Boolean
)
{
    companion object {
        var CurrentProfile: Profile = Profile(0, "", "", "", false)
    }
}