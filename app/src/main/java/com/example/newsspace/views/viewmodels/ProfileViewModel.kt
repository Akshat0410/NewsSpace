package com.example.newsspace.views.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsspace.models.User
import com.example.newsspace.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    var user: LiveData<User>? = null
    var repository: FirebaseRepository = FirebaseRepository()


    fun getCurrentLoggedInUser(uId: String) {

        viewModelScope.launch(Dispatchers.IO) {
            Log.d("data aa gya",user.toString())
            user = repository.getCurrentUserWithUid(uId)

        }
    }


}