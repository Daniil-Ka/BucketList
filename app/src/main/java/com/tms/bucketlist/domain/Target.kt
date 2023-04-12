package com.tms.bucketlist.domain

data class Target(
    val id: Long,
    val name: String,
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
