package com.cjnet.peepsworld.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Creator(
    @Expose
    @SerializedName("creatorID")
    val creatorID: String,

    @Expose
    @SerializedName("creatorFullName")
    val creatorFullName: String
)