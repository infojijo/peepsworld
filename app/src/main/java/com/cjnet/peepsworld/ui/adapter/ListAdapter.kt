package com.cjnet.peepsworld.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cjnet.peepsworld.R
import com.cjnet.peepsworld.models.Feed

class ListAdapter(
    private var list: List<Feed>,
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
        var movie: Feed = list[position]

        if (movie.post_type == 0) {
            hold = holder as FeedViewHolder
            hold.bind(movie)
            hold.mLike?.setOnClickListener {
                onLikeClick(list.get(position).post_feed_id)
                if(hold.mLike?.drawable?.constantState == ContextCompat.getDrawable(mContext, R.drawable.ic_like)?.constantState){
                  hold.mLike?.setImageResource(R.drawable.ic_liked);
                    list.get(position).post_liked_from_server = true
                }
               else{
                  hold.mLike?.setImageResource(R.drawable.ic_like);
                    list.get(position).post_liked_from_server = false
              }
            }
            if(list.get(position).post_liked_from_server){
                hold.mLike?.setImageResource(R.drawable.ic_liked);
            }
            else{
                hold.mLike?.setImageResource(R.drawable.ic_like);
            }
        } else if (movie.post_type == 2) {
            holdURL = holder as FeedURLHolder
            holdURL.bind(movie, mContext)
            holdURL.mLike?.setOnClickListener {
                onLikeClick(list.get(position).post_feed_id)
            }
        } else {
            holdhead = holder as FeedHeadHolder
            holdhead.bind(movie, mContext)
        }
    }

    fun onLikeClick(id:String){

        Toast.makeText(mContext, "You clicked "+id+"th Feed", Toast.LENGTH_SHORT).show()
        //network call for like/unlike

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