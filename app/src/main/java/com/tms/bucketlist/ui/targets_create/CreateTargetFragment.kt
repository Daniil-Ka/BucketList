package com.tms.bucketlist.ui.targets_create

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tms.bucketlist.R
import com.tms.bucketlist.TargetsRepository
import com.tms.bucketlist.databinding.FragmentCreateTargetBinding
import com.tms.bucketlist.domain.Category
import com.tms.bucketlist.domain.Privacy
import com.tms.bucketlist.domain.Target
import com.tms.bucketlist.domain.Todo
import okhttp3.internal.notifyAll
import java.util.*


class CreateTargetFragment : DialogFragment() {
    private var currentTarget : Target? = null
    private val args: CreateTargetFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (args.Id.toInt() != -1) {
                currentTarget = TargetsRepository.instance.getTargetById(args.Id)
            }

    }
    private fun fillList(): MutableList<Todo> {
        val data = mutableListOf<Todo>()
        (0..30).forEach { i -> data.add(Todo("name", i.toString(), false)) }
        return data
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_create_target, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nameView = view.findViewById<EditText>(R.id.target_add_name)
        val deadlineView = view.findViewById<EditText>(R.id.target_add_date)
        val desctiptionView = view.findViewById<EditText>(R.id.target_add_description)
        val budgetView = view.findViewById<EditText>(R.id.target_add_cost)
        desctiptionView.setMovementMethod(ScrollingMovementMethod())
        //region deadline logic
        deadlineView.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                requireContext(),
                { view, pickedYear, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our text view.
                    deadlineView.setText(
                        (dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + pickedYear))
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
        //endregion

        //region layout
        val binding = FragmentCreateTargetBinding.bind(view)
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //endregion

        //region recycler logic
        val recyclerView: RecyclerView? = view.findViewById<RecyclerView>(R.id.todo_recycler)
        recyclerView?.layoutManager = LinearLayoutManager(this.context)
        var todos = mutableListOf<Todo>()
        if (currentTarget != null) {
            todos = currentTarget!!.todo.toMutableList()
        }
        val adapter = TodoAdapter(todos)
        recyclerView?.adapter = adapter

        val addButton = view.findViewById<ImageButton>(R.id.add_todo_button)
        val todoDesc = view.findViewById<EditText>(R.id.add_todo)
        addButton.setOnClickListener {
            val text = todoDesc.text.toString()
            if (text == "")
                return@setOnClickListener
            adapter.addTodo(text)
            todoDesc.setText("")
        }
        //endregion

        //region fill old values
        if (currentTarget != null){
            nameView.setText(currentTarget!!.name)
            budgetView.setText(currentTarget!!.budget)
            deadlineView.setText(currentTarget!!.deadline)
            desctiptionView.setText(currentTarget!!.description)
        }
        //endregion

        //region buttons
        val exitButton = view.findViewById<ImageButton>(R.id.target_return_button)
        exitButton?.setOnClickListener {
            val adapt = activity?.findViewById<RecyclerView>(R.id.targetsRecyclerView)?.adapter
            adapt?.notifyDataSetChanged()
            dialog?.dismiss()
        }

        val saveButton = view.findViewById<TextView>(R.id.target_save_button)
        saveButton.setOnClickListener {
            if (currentTarget == null) {
                val newId = TargetsRepository.instance.targets.maxBy { t -> t.id }.id + 1
                val target = Target(
                    newId,
                    nameView.text.toString(),
                    deadlineView.text.toString(),
                    desctiptionView.text.toString(),
                    budgetView.text.toString(),
                    todo = todos
                )
                TargetsRepository.instance.addTarget(target)
            }
            else {
                currentTarget!!.name = nameView.text.toString()
                currentTarget!!.description = desctiptionView.text.toString()
                currentTarget!!.todo = todos
                currentTarget!!.budget = budgetView.text.toString()
                currentTarget!!.deadline = deadlineView.text.toString()
            }
            val adapt = activity?.findViewById<RecyclerView>(R.id.targetsRecyclerView)?.adapter
            adapt?.notifyDataSetChanged()
            dismissAllDialogs(parentFragmentManager)
        }
        //endregion
    }

    fun dismissAllDialogs(manager: FragmentManager?) {
        val fragments: List<Fragment> = manager?.getFragments() ?: return
        for (fragment in fragments) {
            if (fragment is DialogFragment) {
                val dialogFragment = fragment as DialogFragment
                dialogFragment.dismissAllowingStateLoss()
            }
            val childFragmentManager: FragmentManager = fragment.getChildFragmentManager()
            if (childFragmentManager != null) dismissAllDialogs(childFragmentManager)
        }
    }
}