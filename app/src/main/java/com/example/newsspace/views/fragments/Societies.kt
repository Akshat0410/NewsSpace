package com.example.newsspace.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.newsspace.R
import com.example.newsspace.databinding.FragmentProfileBinding
import com.example.newsspace.databinding.FragmentSocietiesBinding

class Societies() : Fragment(R.layout.fragment_societies){

    private var _binding: FragmentSocietiesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSocietiesBinding.inflate(inflater, container, false)

        initCreateButton()
        return binding.root
    }

    private fun initCreateButton() {

        binding.createEventButton.setOnClickListener {
            findNavController().navigate(R.id.action_societies_to_createEventFragment)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}