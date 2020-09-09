package com.cjnet.peepsworld.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @Expose
    @SerializedName("success")
    val success: String,

    @Expose
    @SerializedName("message")
    val message: String,

    @Expose
    @SerializedName("userToken")
    val userToken: String,

    @Expose
    @SerializedName("userEmail")
    val userEmail: String,

    @Expose
    @SerializedName("userId")
    val userId: String

)