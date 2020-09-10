package com.cjnet.peepsworld.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LikeCount(

    @Expose
    @SerializedName("FeedId")
    val FeedId: String,

    @Expose
    @SerializedName("LikeCount")
    val LikeCount: String
)