package com.example.newsspace.views.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.newsspace.models.User
import com.example.newsspace.repository.FirebaseRepository
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SignInViewModel : ViewModel() {

    var firebaseRepository : FirebaseRepository = FirebaseRepository()
    var authenticatedUserLiveData: LiveData<User>? = null
    fun signInWithGoogle(googleAuthCredential: AuthCredential?) {
        viewModelScope.launch(Dispatchers.IO){
            authenticatedUserLiveData = firebaseRepository.firebaseSignInWithGoogle(googleAuthCredential)
        }

    }


}