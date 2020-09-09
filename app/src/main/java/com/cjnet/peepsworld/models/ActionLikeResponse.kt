package com.cjnet.peepsworld.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ActionLikeResponse (

    @Expose
    @SerializedName("userId")
    val userId: String,

    @Expose
    @SerializedName("feedId")
    val feedId: String,

    @Expose
    @SerializedName("status")
    val status: String

)
