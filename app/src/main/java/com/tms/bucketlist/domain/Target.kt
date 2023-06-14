package com.tms.bucketlist.domain

import androidx.annotation.Keep
import kotlinx.serialization.Serializable
import java.util.Date

@Keep
@Serializable
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
        get() {
            if (todo.isEmpty())
                return 0f
            return todo.count { todo -> todo.isCompeted }.toFloat() / todo.count()
        }
}
