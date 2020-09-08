package com.cjnet.peepsworld.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserLikes(

    @Expose
    @SerializedName("userId")
    val userId: String,

    @Expose
    @SerializedName("feedId")
    val feedId: String,

    @Expose
    @SerializedName("commentText")
    val commentText:String
)