package com.example.newsspace.models

data class Event(
    var eventId: String = "",
    var eventName: String = "",
    var eventDesc: String = "",
    var eventPic: String = "",
    var eventDate: String = "",
    var timePosted: Long = 0L,
    var societyId: String? = null,
    var societyName: String? = null
)
