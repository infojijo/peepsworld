package com.cjnet.peepsworld.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cjnet.peepsworld.R
import com.cjnet.peepsworld.models.Feed
import io.github.ponnamkarthik.richlinkpreview.*

class FeedURLHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(
        inflater.inflate(
            R.layout.feed_item_url_layout,
            parent,
            false
        )
    ) {
    private var mPostComment: TextView? = null
    private var mProfile: TextView? = null
    private var richLinkViewTel: ImageView
    var mLike: ImageView? = null

    init {
        mPostComment = itemView.findViewById(R.id.post_text)
        mProfile = itemView.findViewById(R.id.pp_name)
        richLinkViewTel = itemView.findViewById(R.id.post_extra)
        mLike = itemView.findViewById(R.id.action_like)
    }

    fun bind(movie: Feed, mContext: Context) {
        Glide.get(mContext).clearMemory()
        mPostComment?.text = movie.post_text.toString()
        mProfile?.text = movie.profile_name.toString()

        val richPreview = RichPreview(object : ResponseListener {
            override fun onData(metaData: MetaData) {
                Log.d("FEED", metaData.toString())
                //Implement your Layout
                if (metaData.imageurl != null)
                    Glide.with(mContext)
                        .load(metaData.imageurl)
                        .placeholder(R.drawable.ic_loading)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .into(richLinkViewTel)
            }
            override fun onError(e: Exception) {
                //handle error
                Glide.with(mContext)
                    .load(R.drawable.ic_loading)
                    .into(richLinkViewTel)
            }
        })
        Glide.with(mContext)
            .load(R.drawable.ic_loading)
            .into(richLinkViewTel)
            richPreview.getPreview(movie.post_url.toString())
    }

}