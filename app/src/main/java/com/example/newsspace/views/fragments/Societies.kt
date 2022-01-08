package com.example.newsspace.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.newsspace.R
import com.example.newsspace.dao.UserDao
import com.example.newsspace.databinding.FragmentProfileBinding
import com.example.newsspace.databinding.FragmentSocietiesBinding
import com.example.newsspace.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class Societies : Fragment() {

    private var _binding: FragmentSocietiesBinding? = null
    private val binding get() = _binding!!
    private val auth = Firebase.auth
    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentSocietiesBinding.inflate(inflater, container, false)

        initGetUser()
        initCreateButton()

        return binding.root
    }

    private fun initGetUser() {
        var currentUserId = auth.currentUser!!.uid
        val userDao = UserDao()
        GlobalScope.launch {
            user = userDao.getUserById(currentUserId).await().toObject(User::class.java)!!
        }
    }

    private fun initCreateButton() {

        binding.createEventButton.setOnClickListener {
            if (user!!.canPost) {
                findNavController().navigate(R.id.action_societies_to_createEventFragment)
            } else {
                Toast.makeText(
                    requireContext(),
                    "You do not have permissions to access this feature.",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}