package com.tms.bucketlist.ui.targets.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tms.bucketlist.R
import com.tms.bucketlist.databinding.ItemTargetBinding
import com.tms.bucketlist.domain.Target

class TargetsAdapter : RecyclerView.Adapter<TargetsAdapter.TargetsViewHolder>() {

    var data: List<Target> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class TargetsViewHolder(val binding: ItemTargetBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TargetsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTargetBinding.inflate(inflater, parent, false)

        return TargetsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TargetsViewHolder, position: Int) {
        val target = data[position] // Получение цели из списка данных по позиции
        val context = holder.itemView.context

        with(holder.binding) {

            nameTextView.text = target.name // Отрисовка имени пользователя
            companyTextView.text = target.description // Отрисовка компании пользователя

            Glide.with(context).load(target.photoUrl).circleCrop() // Отрисовка фотографии
                .error(R.drawable.circle) // TODO текстура с ошибкой
                .placeholder(R.drawable.circle)
                .into(imageView)
        }
    }
}