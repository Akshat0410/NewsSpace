package com.example.newsspace.views.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsspace.EventAdapter
import com.example.newsspace.dao.EventsDao
import com.example.newsspace.dao.UserDao
import com.example.newsspace.databinding.FragmentFeedsBinding
import com.example.newsspace.models.Event
import com.example.newsspace.models.User
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*


class FeedsFragment() : Fragment(){

    private var _binding: FragmentFeedsBinding? = null
    private val binding get() = _binding!!
    private val auth = Firebase.auth
    private var user: User? = null
    private lateinit var adapter: EventAdapter
    private  var eventDao: EventsDao=EventsDao()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFeedsBinding.inflate(inflater, container, false)
        initGetUser()
        initSeUI()
        setUpRecyclerView()
        return binding.root
    }

    private fun setUpRecyclerView() {
        val eventCollection=eventDao.postCollection
        val query=eventCollection.orderBy("timePosted",Query.Direction.DESCENDING)
        val recyclerViewOption=FirestoreRecyclerOptions.Builder<Event>().setQuery(query,Event::class.java).build()
        adapter= EventAdapter(recyclerViewOption)
        binding.eventsListing.adapter=adapter
        binding.eventsListing.layoutManager=LinearLayoutManager(requireContext())
    }

    private fun initSeUI() {
        binding.userName.text=user?.userName
    }

    private fun initGetUser() {
        var currentUserId = auth.currentUser!!.uid
        val userDao = UserDao()
        GlobalScope.launch {
            user = userDao.getUserById(currentUserId).await().toObject(User::class.java)!!
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onResume() {
        super.onResume()
        initGetUser()
    }
}
