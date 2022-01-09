package com.example.newsspace.views.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsspace.models.User
import com.example.newsspace.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel : ViewModel() {

    var user: LiveData<User>? = null
    var userM: MutableLiveData<User> =MutableLiveData()
    var repository: FirebaseRepository = FirebaseRepository()


    fun getCurrentLoggedInUser(uId: String) {

        viewModelScope.launch(Dispatchers.IO) {



        }


    }


}