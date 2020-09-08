package com.cjnet.peepsworld.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cjnet.peepsworld.R
import com.cjnet.peepsworld.models.Feed

class FeedAdapter(
    private val list: List<Feed>,
    private val mContext: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            return FeedViewHolder(inflater, parent)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var hold: FeedViewHolder
        val movie: Feed = list[position]

            hold = holder as FeedViewHolder
            hold.bind(movie)
            hold.mLike?.setOnClickListener {
                onLikeClick(list.get(position).post_feed_id)
                if(hold.mLike?.drawable?.constantState == ContextCompat.getDrawable(mContext, R.drawable.ic_like)?.constantState){
                    hold.mLike?.setImageResource(R.drawable.ic_liked);
                }
                else{
                    hold.mLike?.setImageResource(R.drawable.ic_like);
                }
            }
//            if(list.get(position).post_liked_from_server){
//                hold.mLike?.setImageResource(R.drawable.ic_liked);
//            }
    }

    fun onLikeClick(id:String){

        Toast.makeText(mContext, "You clicked "+id+"th Feed", Toast.LENGTH_SHORT).show()
        //network call for like/unlike

    }



    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return list.size;
    }

    override fun getItemId(position: Int): Long {
        return list.size.toLong()
    }

}