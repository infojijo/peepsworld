package com.cjnet.peepsworld.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Feeds(
    @Expose
    @SerializedName("feedLikeCount")
    val feedLikeCount: String,

    @Expose
    @SerializedName("feedID")
    val feedID: String,

    @Expose
    @SerializedName("feedLevel")
    val feedLevel: String,

    @Expose
    @SerializedName("feedCommentCount")
    val feedCommentCount: String,

    @Expose
    @SerializedName("feedPostID")
    val feedPostID: String,

    @Expose
    @SerializedName("creator")
    val creator: Creator,

    @Expose
    @SerializedName("Post")
    val post: Post,

    @Expose
    @SerializedName("Poll")
    val poll: Poll


)