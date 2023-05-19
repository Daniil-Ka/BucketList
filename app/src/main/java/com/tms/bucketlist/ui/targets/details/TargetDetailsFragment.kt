package com.tms.bucketlist.ui.targets.details

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.tms.bucketlist.R
import com.tms.bucketlist.TargetsRepository
import com.tms.bucketlist.databinding.FragmentDetailsTargetBinding

class TargetDetailsFragment : DialogFragment() {

    private val args: TargetDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailsTargetBinding.bind(view)
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val target = TargetsRepository.instance.getTargetById(args.Id)
        if (target != null) {
            val target_name = view.findViewById<TextView>(R.id.target_name)
            target_name?.text = target.name
            // TODO ДОПИСАТЬ ВЫВОД ПОДЗАДАЧ СПИСКОМ, ВЫПОЛНЕННЫЕ ЗЕЛЕНЫМ
            //             textView?.setTextColor(Color.GREEN)
            // TODO Дедлайн для задачи и участники нужно добавить в класс цели
            val target_descripton = view.findViewById<TextView>(R.id.target_description)
            target_descripton?.text = "Заметки: " + target.description
        }

        // TODO НЕ РАБОТАЛ ВОЗВРАТ ЧЕРЕЗ НАВИГАЦИЮ (КОД ВСЕ ЕЩЕ ОСТАВЛСЯ ТАМ)
        // TODO Оставил через прямое закрытие
        val exitButton = view.findViewById<ImageButton>(R.id.target_return_button)
        exitButton?.setOnClickListener { dialog?.dismiss() }

        // задний фон
        val targetsLayout = view.findViewById<ConstraintLayout>(R.id.target_main_layout)
        targetsLayout?.setOnClickListener { dialog?.dismiss() }


            //binding.goBackButton.setOnClickListener {
            //    findNavController().popBackStack()
            // }
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_details_target, container, false)
        }
}