package com.example.newsspace.repository
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.MutableLiveData
import com.example.newsspace.models.User
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class FirebaseRepository {

    private val auth = FirebaseAuth.getInstance()
    private var authenticatedUserMutableLiveData: MutableLiveData<User> = MutableLiveData<User>()
    private var currentUserMutableLiveData: MutableLiveData<User> = MutableLiveData<User>()
    private val db= FirebaseFirestore.getInstance()
    private val userCollection = db.collection("users")


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
            currentUserMutableLiveData.value=user
        }

        return authenticatedUserMutableLiveData
    }


    suspend fun getCurrentUserWithUid(uId: String): MutableLiveData<User> {
        userCollection.document(uId).get().addOnSuccessListener {
            currentUserMutableLiveData.value = it.toObject<User>()
            Log.d("Success", currentUserMutableLiveData.value.toString())

        }.addOnFailureListener {
            Log.d("failure nahi chala ", currentUserMutableLiveData.value.toString())
        }

        return currentUserMutableLiveData
    }


}
