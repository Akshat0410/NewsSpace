package com.example.newsspace.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newsspace.databinding.FragmentProfileBinding
import com.example.newsspace.models.User
import com.example.newsspace.views.viewmodels.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

class Profile : Fragment() {


    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : ProfileViewModel
    private var auth : FirebaseAuth= FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        initProfileViewModel()
        auth.currentUser?.let { handleUserProfileData(it.uid) }
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


    private fun handleUserProfileData(uId: String) {

        viewModel.getCurrentLoggedInUser(uId)
        viewModel.user?.observe(requireActivity(), Observer{
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