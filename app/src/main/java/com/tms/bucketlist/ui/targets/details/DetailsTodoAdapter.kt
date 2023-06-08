package com.tms.bucketlist.ui.targets.details

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tms.bucketlist.R
import com.tms.bucketlist.domain.Todo

public class DetailsTodoAdapter(private val todo: MutableList<Todo>) :
    RecyclerView.Adapter<DetailsTodoAdapter.TodoVholder>() {

    class TodoVholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val description = itemView.findViewById<TextView>(R.id.details_todo)
        val toggleButton = itemView.findViewById<ImageButton>(R.id.details_todo_toggle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoVholder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_details_todo, parent, false)
        return TodoVholder(itemView)
    }

    override fun getItemCount(): Int {
        return todo.size
    }

    override fun onBindViewHolder(holder: TodoVholder, position: Int) {
        holder.description.setText(todo[position].description)
        if (todo[position].isCompeted) {
            holder.description.setTextColor(Color.GREEN)
            holder.toggleButton.setImageResource(R.drawable.circle_green)
        }
        else {
            holder.description.setTextColor(Color.BLACK)
            holder.toggleButton.setImageResource(R.drawable.circle_transparent)
        }
        holder.toggleButton.setOnClickListener {
            todo[position].isCompeted = !todo[position].isCompeted
            notifyItemChanged(position)
        }
    }
}