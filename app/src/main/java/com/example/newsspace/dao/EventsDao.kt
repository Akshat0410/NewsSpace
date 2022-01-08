package com.example.newsspace.dao

import com.example.newsspace.models.Event
import com.example.newsspace.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class EventsDao {

    private val db = FirebaseFirestore.getInstance()
    private val postCollection = db.collection("events")
    private val auth = Firebase.auth

    fun createEvent(title: String, eventDesc: String, eventPic: String, eventDate: String) {

        var currentUserId = auth.currentUser!!.uid
        val userDao = UserDao()

        GlobalScope.launch {
            val user = userDao.getUserById(currentUserId).await().toObject(User::class.java)
            val currTime = System.currentTimeMillis()
            val event = Event("", title, eventDesc, eventPic, eventDate, currTime, null, user!!.uId)
            postCollection.document(user.uId).set(event)
        }


    }
}