package com.cjnet.peepsworld.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Poll(
    @Expose
    @SerializedName("pollId")
    val pollId: String,
    @Expose
    @SerializedName("pollQuestion")
    val pollQuestion: String,
    @Expose
    @SerializedName("pollOptions")
    val pollOptions: List<PollOptions>
)