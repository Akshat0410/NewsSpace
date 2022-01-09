package com.example.newsspace.views.fragments

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.newsspace.R
import com.example.newsspace.dao.EventsDao
import com.example.newsspace.databinding.FragmentCreateEventBinding
import com.example.newsspace.helper.DatePickerFragment
import com.example.newsspace.models.Event
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import androidx.core.app.ActivityCompat.startActivityForResult

import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.navigation.fragment.findNavController


class CreateEventFragment : Fragment(), AdapterView.OnItemSelectedListener{

    private var _binding: FragmentCreateEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var eventTitle : TextInputEditText
    private lateinit var eventDesc : TextInputEditText
    private lateinit var eventDate : TextInputEditText
    private lateinit var addImage : Button
    private lateinit var postEvent : Button
    private lateinit var eventImage : ImageView
    private lateinit var society : Spinner

    private lateinit var imageString : String

    private var RESULT_LOAD_IMAGE=123
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            val uri=data.getData()
            eventImage.setImageURI(uri)
            imageString=uri.toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCreateEventBinding.inflate(inflater, container, false)

        setUpSpinner()
        initWidgets()



        binding.postButton.setOnClickListener {
            handlePost()
        }

        binding.addImage.setOnClickListener {
            val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(i, RESULT_LOAD_IMAGE)
        }
        return binding.root
    }

    private fun initWidgets() {
        eventTitle = binding.eventName
        eventDesc = binding.eventDesc
        society = binding.societyName
        eventImage = binding.eventImage
        addImage = binding.addImage
        postEvent = binding.postButton
        eventDate = binding.eventDate
    }

    private fun handlePost() {
        val eventDao=EventsDao()
        val etitle=eventTitle.text.toString().trim()
        val edesc=eventDesc.text.toString().trim()
        val edate=eventDate.text.toString().trim()
        if(etitle.isNotEmpty() && edesc.isNotEmpty() && edate.isNotEmpty()){
            eventDao.createEvent(etitle,edesc,imageString,edate)
            binding.progressBar.visibility=View.VISIBLE
            findNavController().navigate(R.id.action_createEventFragment_to_feedsFragment)
        }else{
            Toast.makeText(requireContext(),"Some Data Missing",Toast.LENGTH_SHORT).show()
        }


    }

    private fun setUpDatePicker(dateContainer: TextInputLayout) {
        val newFragment = DatePickerFragment()
        val supportFragmentManager : FragmentManager=parentFragmentManager
        newFragment.show(supportFragmentManager, "datePicker")
    }

    private fun setUpSpinner() {
        val spinner: Spinner = binding.societyName
        spinner.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.society_name,
            android.R.layout.simple_spinner_item
        ).also { adapter ->

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
         p0?.getItemAtPosition(p2)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }


}