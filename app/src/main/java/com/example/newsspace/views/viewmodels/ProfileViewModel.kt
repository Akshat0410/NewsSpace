package com.example.newsspace.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.newsspace.models.User
import com.example.newsspace.repository.FirebaseRepository

class ProfileViewModel : ViewModel() {

     lateinit var user : LiveData<User>
     var firebaseRepository : FirebaseRepository = FirebaseRepository()


     fun getCurrentUser() : LiveData<User>{
          user=firebaseRepository.getCurrentUser()
          return user
     }




}