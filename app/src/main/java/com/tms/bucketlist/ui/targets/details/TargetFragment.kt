package com.tms.bucketlist.ui.targets.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.tms.bucketlist.R
import com.tms.bucketlist.databinding.FragmentTargetBinding

class TargetFragment : Fragment() {

    private val args: TargetFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val targetId = args.Id
        Toast.makeText(context, targetId.toString(), Toast.LENGTH_LONG).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentTargetBinding.bind(view)

        binding.root

        /*
                super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCatDetailsBinding.bind(view)
        viewModel.catLiveData.observe(viewLifecycleOwner) { cat ->
            binding.catNameTextView.text = cat.name
            binding.catDescriptionTextView.text = cat.description
            binding.catImageView.load(cat.photoUrl) {
                transformations(CircleCropTransformation())
                placeholder(R.drawable.circle)
            }
            binding.favoriteImageView.setImageResource(
                if (cat.isFavorite) R.drawable.ic_favorite
                else R.drawable.ic_favorite_not
            )
            binding.favoriteImageView.setTintColor(
                if (cat.isFavorite) R.color.highlighted_action
                else R.color.action
            )
        }


        binding.favoriteImageView.setOnClickListener {
            viewModel.toggleFavorite()
        }
         */
        //binding.goBackButton.setOnClickListener {
        //    findNavController().popBackStack()
        // }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_target, container, false)
    }
}