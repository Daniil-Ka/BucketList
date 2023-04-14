package com.tms.bucketlist.ui.targets

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tms.bucketlist.R
import com.tms.bucketlist.TargetsRepository
import com.tms.bucketlist.databinding.FragmentTargetsBinding
import com.tms.bucketlist.domain.Target
import com.tms.bucketlist.ui.targets.adapter.TargetsAdapter


class TargetsFragment : Fragment() {

    private var _binding: FragmentTargetsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //val homeViewModel = ViewModelProvider(this)[TargetsViewModel::class.java]

        _binding = FragmentTargetsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView = root.findViewById<RecyclerView>(R.id.targetsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity);

        var adapter = TargetsAdapter(object : TargetsActionListener { // Создание объекта
            override fun onTargetClick(target: Target) {
                val direction = TargetsFragmentDirections
                    .actionNavigationTargetsToTargetFragment(target.id)
                findNavController().navigate(direction)
            }

            override fun onPersonGetId(target: Target) {
                TODO("Not yet implemented")
            }

            override fun onTargetRemove(target: Target) {
                TargetsRepository.instance.removeTarget(target)
            }
        })

        // TODO: извиняюсь за Singleton
        TargetsRepository.instance.addListener {
            adapter.data = TargetsRepository.instance.targets
        }



        recyclerView.adapter = adapter
        recyclerView.itemAnimator = DefaultItemAnimator()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}