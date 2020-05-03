package com.cjnet.peepsworld.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Post(
    @Expose
    @SerializedName("postId")
    val postId: String,
    @Expose
    @SerializedName("postLink")
    val postLink: String,
    @Expose
    @SerializedName("postTitle")
    val postTitle: String,
    @Expose
    @SerializedName("Media")
    val media: List<Media>
)