package com.cjnet.peepsworld.models

//Kotlin Object for holding the feed

data class Feed(
    val post_feed_id: String,
    val post_text: String,
    val profile_name: String,
    val post_type: Int,
    val post_url: String,
    val post_likes : String,
    val post_comments :String,
    var post_liked_from_server:Boolean = false
)

