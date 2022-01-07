package com.example.newsspace.repository
import androidx.lifecycle.MutableLiveData
import com.example.newsspace.models.User
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class FirebaseRepository {

    private val auth = FirebaseAuth.getInstance()
    var authenticatedUserMutableLiveData: MutableLiveData<User> = MutableLiveData<User>()


    suspend fun firebaseSignInWithGoogle(googleAuthCredential: AuthCredential?): MutableLiveData<User>? {
        val auth = auth.signInWithCredential(googleAuthCredential!!).await()
        val firebaseUser = auth.user
        val user = firebaseUser?.uid?.let {
            User(
                it,
                firebaseUser.displayName,
                firebaseUser.photoUrl.toString(),
                firebaseUser.email,
                firebaseUser.phoneNumber,
                false
            )


        }

        withContext(Dispatchers.Main){
            authenticatedUserMutableLiveData.value = user
        }

        return authenticatedUserMutableLiveData
    }

    fun getCurrentUser() : MutableLiveData<User>{
        return authenticatedUserMutableLiveData
    }


}
