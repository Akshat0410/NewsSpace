package com.example.newsspace

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsspace.models.Event
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class EventAdapter(options: FirestoreRecyclerOptions<Event>) : FirestoreRecyclerAdapter<Event, EventAdapter.EventViewHolder>(
    options
) {

    class EventViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val eventTitle : TextView=itemView.findViewById(R.id.eventTitle)
        val eventDate : TextView=itemView.findViewById(R.id.eventDateList)
        val eventImage : ImageView =itemView.findViewById(R.id.eventImageView)
        val eventDesc : TextView =itemView.findViewById(R.id.eventDescriptionList)
        val eventRegister : Button=itemView.findViewById(R.id.buttonRegister)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.event_list,parent,false))
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int, model: Event) {
        holder.eventTitle.text=model.eventName
        holder.eventDesc.text=model.eventDesc
        holder.eventDate.text=model.eventDate
        Glide.with(holder.eventImage.context).load(model.eventPic).into(holder.eventImage)
        holder.eventRegister.setOnClickListener {

        }
    }
}