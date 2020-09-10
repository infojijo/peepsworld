package com.cjnet.peepsworld.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cjnet.peepsworld.R
import com.cjnet.peepsworld.models.Feed

class FeedViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(
        inflater.inflate(
            R.layout.feed_item_layout,
            parent,
            false
        )
    ) {
    var mPostComment: TextView? = null
    var mProfile: TextView? = null
    var mLike: ImageView? = null
    var mCountLike: TextView? = null
    var mCountComment: TextView? = null


    init {
        mPostComment = itemView.findViewById(R.id.post_text)
        mProfile = itemView.findViewById(R.id.pp_name)
        mLike = itemView.findViewById(R.id.action_like)
        mCountLike= itemView.findViewById(R.id.count_likes)
        mCountComment = itemView.findViewById(R.id.count_comments)

    }

    fun bind(movie: Feed) {
        mPostComment?.text = movie.post_text.toString()
        mProfile?.text = movie.profile_name.toString()
        mCountLike?.text = movie.post_likes.toString()
        mCountComment?.text = movie.post_comments.toString()

    }

}