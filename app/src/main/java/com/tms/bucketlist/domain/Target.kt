package com.tms.bucketlist.domain

import java.util.Date

data class Target(
    val id: Long,
    val name: String,
    //val endDate: Date,
    val description: String,
    val photoUrl: String,
    val category: Category,
    var privacy: Privacy,
    val todo: List<Todo>,
)
{
    val isCompleted: Boolean
        get() = todo.all { todo -> todo.isCompeted }

    val progress: Float
        get() = todo.count { todo -> todo.isCompeted }.toFloat() / todo.count()
}
