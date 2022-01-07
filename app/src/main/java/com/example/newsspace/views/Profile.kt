package com.example.newsspace.views

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newsspace.databinding.FragmentProfileBinding
import com.example.newsspace.models.User
import com.example.newsspace.views.viewmodels.ProfileViewModel
import com.example.newsspace.views.viewmodels.SignInViewModel
import com.google.gson.Gson

class Profile : Fragment() {


    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var user : User
    private lateinit var viewModel : ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        initProfileViewModel()
        handleUserProfileData()
        handleEditClicks()

        return binding.root
    }

    private fun initProfileViewModel() {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    private fun handleEditClicks() {
       binding.editProfile.setOnClickListener {
           Toast.makeText(requireContext(),"We don't have edit features now.Will bring shortly !!",Toast.LENGTH_SHORT).show()
       }
    }


    private fun handleUserProfileData() {
        viewModel.getCurrentUser().observe(viewLifecycleOwner, Observer {
            binding.userName.text=it.userName
            binding.userEmail.text=it.userEmail
            binding.userPhone.text=it.userPhone
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}