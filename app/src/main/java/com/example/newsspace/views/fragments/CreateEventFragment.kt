package com.example.newsspace.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsspace.R
import com.example.newsspace.databinding.FragmentCreateEventBinding
import com.example.newsspace.databinding.FragmentProfileBinding

class CreateEventFragment : Fragment(){

    private var _binding: FragmentCreateEventBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCreateEventBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}