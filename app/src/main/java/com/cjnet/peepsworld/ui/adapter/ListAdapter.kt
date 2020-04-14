package com.cjnet.peepsworld.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cjnet.peepsworld.models.Feed

class ListAdapter(private val list: List<Feed>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == 0) {
            return FeedViewHolder(inflater, parent)
        } else {
            return FeedHeadHolder(inflater, parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var hold: FeedViewHolder
        var holdhead: FeedHeadHolder
        val movie: Feed = list[position]
        if (movie.post_type == 0) {
            hold = holder as FeedViewHolder
            hold.bind(movie)
        } else {
            holdhead = holder as FeedHeadHolder
            holdhead.bind(movie)
        }

    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        if (list[position].post_type == 1) {
            return 1;
        } else {
            return 0;
        }
    }

}