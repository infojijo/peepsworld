package com.cjnet.peepsworld.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AllFeedsResponse(  @Expose
                              @SerializedName("Feeds")
                              val feeds: List<Feeds>)