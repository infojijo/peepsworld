package com.cjnet.peepsworld.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PollOptions(
    @Expose
    @SerializedName("pollOptionID")
    val pollOptionID: String,
    @Expose
    @SerializedName("pollOptionText")
    val pollOptionText: String
)
