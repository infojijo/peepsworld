package com.cjnet.peepsworld.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.cjnet.peepsworld.R
import com.cjnet.peepsworld.models.Feed

class ListAdapter(
    private val list: List<Feed>,
    private val mContext: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == 0) {
            return FeedViewHolder(inflater, parent)
        } else if (viewType == 2) {
            return FeedURLHolder(inflater, parent)
        } else {
            return FeedHeadHolder(inflater, parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var hold: FeedViewHolder
        var holdURL: FeedURLHolder
        var holdhead: FeedHeadHolder
        val movie: Feed = list[position]
        if (movie.post_type == 0) {
            hold = holder as FeedViewHolder
            hold.bind(movie, mContext)
            hold.mLike?.setOnClickListener {
                onLikeClick()
            }


        } else if (movie.post_type == 2) {
            holdURL = holder as FeedURLHolder
            holdURL.bind(movie, mContext)
            holdURL.mLike?.setOnClickListener {
                onLikeClick()
            }

        } else {
            holdhead = holder as FeedHeadHolder
            holdhead.bind(movie, mContext)
        }


    }

    fun onLikeClick(){
        Toast.makeText(mContext, "You clicked me.", Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        if (list[position].post_type == 1) {
            return 1;
        } else if (list[position].post_type == 2) {
            return 2;
        } else {
            return 0;
        }
    }

}