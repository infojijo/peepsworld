package com.cjnet.peepsworld.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
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
    private var mPostComment: TextView? = null
    private var mProfile: TextView? = null

    init {
        mPostComment = itemView.findViewById(R.id.post_text)
        mProfile = itemView.findViewById(R.id.pp_name)
    }

    fun bind(movie: Feed, mContext: Context) {
        mPostComment?.text = movie.post_text.toString()
        mProfile?.text = movie.profile_name.toString()
    }

}