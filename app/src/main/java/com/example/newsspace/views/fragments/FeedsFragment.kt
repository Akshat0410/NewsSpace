package com.example.newsspace.views.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsspace.dao.UserDao
import com.example.newsspace.databinding.FragmentFeedsBinding
import com.example.newsspace.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class FeedsFragment() : Fragment(){

    private var _binding: FragmentFeedsBinding? = null
    private val binding get() = _binding!!
    private val auth = Firebase.auth
    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFeedsBinding.inflate(inflater, container, false)
        initGetUser()
        initSeUI()
        return binding.root
    }

    private fun initSeUI() {
        binding.userName.text=user?.userName
    }

    private fun initGetUser() {
        var currentUserId = auth.currentUser!!.uid
        val userDao = UserDao()
        GlobalScope.launch {
            user = userDao.getUserById(currentUserId).await().toObject(User::class.java)!!
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onResume() {
        super.onResume()
        initGetUser()
    }
}
