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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tms.bucketlist.R
import com.tms.bucketlist.TargetsRepository
import com.tms.bucketlist.databinding.FragmentDetailsTargetBinding
import com.tms.bucketlist.domain.Todo
import com.tms.bucketlist.ui.targets.TargetsFragment
import com.tms.bucketlist.ui.targets.TargetsFragmentDirections
import com.tms.bucketlist.ui.targets.details.TargetDetailsFragmentArgs
import com.tms.bucketlist.ui.targets.details.TargetDetailsFragmentDirections
import com.tms.bucketlist.ui.targets_create.TodoAdapter

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

            val target_descripton = view.findViewById<TextView>(R.id.target_description)
            target_descripton?.text = "Заметки: " + target.description
        }

        val exitButton = view.findViewById<ImageButton>(R.id.target_return_button)
        exitButton?.setOnClickListener { dialog?.dismiss() }

        val targetsLayout = view.findViewById<ConstraintLayout>(R.id.target_main_layout)
        targetsLayout?.setOnClickListener { dialog?.dismiss() }

        val editButton = view.findViewById<ImageButton>(R.id.target_edit_button)
        editButton.setOnClickListener {
            val dir = TargetDetailsFragmentDirections.targetEditButton(target!!.id)
            findNavController().navigate(dir)
        }

        val recyclerView: RecyclerView? = view.findViewById<RecyclerView>(R.id.details_todo_recycler)
        recyclerView?.layoutManager = LinearLayoutManager(this.context)
        val adapter = DetailsTodoAdapter(target!!.todo.toMutableList())
        recyclerView?.adapter = adapter
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_details_target, container, false)
        }
}