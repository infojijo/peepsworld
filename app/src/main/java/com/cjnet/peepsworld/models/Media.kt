package com.cjnet.peepsworld.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Media(
    @Expose
    @SerializedName("MediaId")
    val mediaId: String,
    @Expose
    @SerializedName("MediaUrl")
    val mediaUrl: String
)