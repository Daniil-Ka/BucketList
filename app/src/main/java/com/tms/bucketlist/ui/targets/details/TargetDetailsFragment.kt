package com.tms.bucketlist.ui.targets.details

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
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
        //binding.targetCost.setMovementMethod(ScrollingMovementMethod())
        //binding.targetName.setMovementMethod(ScrollingMovementMethod())
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val target = TargetsRepository.instance.getTargetById(args.Id)
        Log.d("tagggg", args.Id.toString())
        if (target != null) {
            val target_name = view.findViewById<TextView>(R.id.target_name)
            target_name?.text = target.name

            val target_descripton = view.findViewById<TextView>(R.id.target_description)
            target_descripton?.text = "Заметки: " + target.description
            //target_descripton.setMovementMethod(ScrollingMovementMethod())

            val target_deadline = view.findViewById<TextView>(R.id.target_deadline)
            target_deadline?.text = "Дедлайн " + target.deadline

            val target_budget = view.findViewById<TextView>(R.id.target_cost)
            target_budget.text = "Бюджет " + target.budget
        }

        val exitButton = view.findViewById<ImageButton>(R.id.target_return_button)
        exitButton?.setOnClickListener {
            dialog?.dismiss()
        }

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

    override fun onDestroyView() {
        super.onDestroyView()
        val adapt = activity?.findViewById<RecyclerView>(R.id.targetsRecyclerView)?.adapter
        activity?.findViewById<ProgressBar>(R.id.mainProgressBar)?.setProgress(TargetsRepository.instance.getTotalProgress())
        adapt?.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details_target, container, false)
    }
}