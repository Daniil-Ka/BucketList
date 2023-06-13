package com.tms.bucketlist.domain

import java.util.Date

data class Target(
    val id: Long,
    var name: String,
    var deadline: String,
    var description: String,
    var budget: String,
    var todo: List<Todo>,
)
{
    val isCompleted: Boolean
        get() = todo.all { todo -> todo.isCompeted }

    val progress: Float
        get() = todo.count { todo -> todo.isCompeted }.toFloat() / todo.count()
}
