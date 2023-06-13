package com.tms.bucketlist.ui.targets.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tms.bucketlist.R
import com.tms.bucketlist.databinding.ItemTargetBinding
import com.tms.bucketlist.domain.Target
import com.tms.bucketlist.ui.targets.TargetsActionListener
import kotlin.math.roundToInt

class TargetsAdapter(
    private val targetsActionListener: TargetsActionListener
) : RecyclerView.Adapter<TargetsAdapter.TargetsViewHolder>(), View.OnClickListener {

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

        binding.root.setOnClickListener(this)
        binding.dedlineTextView.setOnClickListener(this)
        binding.progressTextView.setOnClickListener(this)
        binding.nameTextView.setOnClickListener(this)
        /*binding.likedImageView.setOnClickListener(this)

        holder.itemView.tag = target
        holder.binding.companyTextView.tag = target
        holder.binding.imageView.tag = target
        holder.binding.nameTextView.tag = target
        holder.binding.more.tag = target*/

        return TargetsViewHolder(binding)
    }

    override fun onClick(view: View) {
        val target: Target = view.tag as Target // Получаем из тэга цель

        when (view.id) {
            R.id.more -> targetsActionListener.onTargetRemove(target)
            R.id.likedImageView -> { }
            else -> targetsActionListener.onTargetClick(target)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TargetsViewHolder, position: Int) {
        val target = data[position] // Получение цели из списка данных по позиции
        val context = holder.itemView.context

        with(holder.binding) {
            nameTextView.text = target.name // Отрисовка имени пользователя
            dedlineTextView.text = "Дедлайн " + target.deadline // Отрисовка имени пользователя
            progressTextView.text = (target.progress * 100).roundToInt().toString() + "%" // Отрисовка имени пользователя
            if (target.isCompleted) {
                targetBG.setBackgroundResource(R.drawable.target_done_background)
            }
            else {
                targetBG.setBackgroundResource(R.drawable.white_rectangle_rounded)
            }
            //companyTextView.text = target.description // Отрисовка компании пользователя

            /*
            Glide.with(context).load(target.photoUrl).circleCrop() // Отрисовка фотографии
                .error(R.drawable.circle) // TODO текстура с ошибкой
                .placeholder(R.drawable.circle)
                .into(imageView)
*/
            holder.itemView.tag = target
            holder.binding.dedlineTextView.tag = target
            holder.binding.nameTextView.tag = target
            holder.binding.progressTextView.tag = target
        }
    }
}