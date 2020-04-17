package com.cjnet.peepsworld.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cjnet.peepsworld.R
import com.cjnet.peepsworld.models.Feed

class FeedHeadHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(
        inflater.inflate(
            R.layout.feed_item_header,
            parent,
            false
        )
    ) {
    fun bind(movie: Feed, mContext: Context) {

    }

}