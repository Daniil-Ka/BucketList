package com.tms.bucketlist.ui.targets_create

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tms.bucketlist.R
import com.tms.bucketlist.domain.Todo

public class TodoAdapter(private val todo: MutableList<Todo>) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val description = itemView.findViewById<TextView>(R.id.todo_text)
        val deleteButton = itemView.findViewById<ImageButton>(R.id.todo_delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_to_do, parent, false)
        return TodoViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return todo.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.description.setText(todo[position].description)
        if (todo[position].isCompeted)
            holder.description.setTextColor(Color.GREEN)
        holder.deleteButton.setOnClickListener {
            todo.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, todo.size)
        }
    }

    fun addTodo(description: String) {
        todo.add(todo.size, Todo("name", description, false))
        notifyItemInserted(todo.size - 1);
    }
}