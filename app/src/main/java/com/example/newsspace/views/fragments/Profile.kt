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
import com.example.newsspace.dao.UserDao
import com.example.newsspace.databinding.FragmentProfileBinding
import com.example.newsspace.models.User
import com.example.newsspace.views.viewmodels.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class Profile : Fragment() {


    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val auth = Firebase.auth
    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        initGetUser()
        initSetUI()
        handleEditClicks()

        return binding.root
    }

    private fun initSetUI() {

        binding.userName.text=user?.userName
        binding.userEmail.text=user?.userEmail
        binding.userPhone.text=user?.userPhone
    }

    @DelicateCoroutinesApi
    private fun initGetUser() {
        val currentUserId = auth.currentUser!!.uid
        val userDao = UserDao()
        GlobalScope.launch {
            user = userDao.getUserById(currentUserId).await().toObject(User::class.java)!!
        }
    }



    private fun handleEditClicks() {
       binding.editProfile.setOnClickListener {
           Toast.makeText(requireContext(),"We don't have edit features now.Will bring shortly !!",Toast.LENGTH_SHORT).show()
       }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        initGetUser()
    }
}