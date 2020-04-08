package com.cjnet.peepsworld.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cjnet.peepsworld.models.Feed

class ListAdapter(private val list: List<Feed>)
    : RecyclerView.Adapter<FeedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FeedViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val movie: Feed = list[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = list.size

}