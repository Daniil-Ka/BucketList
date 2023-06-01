package com.tms.bucketlist.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tms.bucketlist.R
import com.tms.bucketlist.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = ProfileData.getInstance(requireContext())
        var nameView = view.findViewById<EditText>(R.id.profileName)
        var ageView = view.findViewById<EditText>(R.id.profileAge)
        var sexView = view.findViewById<EditText>(R.id.profileSex)
        var phoneView = view.findViewById<EditText>(R.id.profilePhoneNumber)
        var emailView = view.findViewById<EditText>(R.id.profileEmail)

        var saveButton = view.findViewById<TextView>(R.id.profileSaveView)
        saveButton.setOnClickListener {
            var oldName = data.name
            var oldAge = data.age
            var oldSex = data.sex
            var oldPhone = data.phoneNumber
            var oldEmail = data.email
            try {
                data.name = nameView.text.toString()
                data.age = ageView.text.toString().toInt()
                data.sex = sexView.text.toString()
                data.phoneNumber = phoneView.text.toString()
                data.email = emailView.text.toString()
                Toast.makeText(requireContext(), "Сохранено", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                data.name = oldName
                data.age = oldAge
                data.sex = oldSex
                data.phoneNumber = oldPhone
                data.email = oldEmail
                nameView.setText(data.name)
                ageView.setText(data.age.toString())
                sexView.setText(data.sex)
                phoneView.setText(data.phoneNumber)
                emailView.setText(data.email)
                Toast.makeText(requireContext(), "Что-то пошло не так...", Toast.LENGTH_SHORT).show()
            }
        }

        nameView.setText(data.name)
        ageView.setText(data.age.toString())
        sexView.setText(data.sex)
        phoneView.setText(data.phoneNumber)
        emailView.setText(data.email)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}