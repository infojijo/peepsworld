package com.cjnet.peepsworld.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AllFeedsResponse(  @Expose
                              @SerializedName("Feeds")
                              val feeds: List<Feeds>)

data class AllLikes(  @Expose
                              @SerializedName("UserLikes")
                              val feeds: List<UserLikes>)

data class AllLikeCount(  @Expose
                      @SerializedName("LikeCount")
                      val feeds: List<LikeCount>)
