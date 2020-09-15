package com.cjnet.peepsworld.ui.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cjnet.peepsworld.R
import com.cjnet.peepsworld.models.Feed
import com.cjnet.peepsworld.network.PeepsWorldServerInterface
import com.cjnet.peepsworld.ui.dashboard.DashboardFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ListAdapter(
    private var list: List<Feed>,
    private val mContext: Context,
    private val fragment: DashboardFragment
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var disposable: Disposable? = null
    val wikiApiServe by lazy {
        PeepsWorldServerInterface.create()
    }
    private val PREF_ID = "user_id_sp"

    var dashFragment:DashboardFragment?=null


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



        val sharedPref: SharedPreferences? = mContext?.getSharedPreferences(PREF_ID, 0)
        val userId: String? = sharedPref?.getString(PREF_ID,"1")

        if (movie.post_type == 0) {
            hold = holder as FeedViewHolder
            hold.bind(movie)
            hold.mLike?.setOnClickListener {
                //onLikeClick(list.get(position).post_feed_id)

                var currentLike:Int = Integer.parseInt(hold.mCountLike?.text.toString())
                var countAfterAction:Int
                if(hold.mLike?.drawable?.constantState == ContextCompat.getDrawable(mContext, R.drawable.ic_like)?.constantState){

                    hold.mLike?.setImageResource(R.drawable.ic_liked);
                    list.get(position).post_liked_from_server = true
                    countAfterAction = currentLike+1
                    list.get(position).post_likes = countAfterAction.toString()
                    hold.mCountLike?.setText(countAfterAction.toString())
                }
                else{
                    hold.mLike?.setImageResource(R.drawable.ic_like);
                    list.get(position).post_liked_from_server = false
                    countAfterAction = currentLike-1
                    list.get(position).post_likes = countAfterAction.toString()
                    hold.mCountLike?.setText(countAfterAction.toString())
                }

                like(userId,list.get(position).post_feed_id)
            }

            if(list.get(position).post_liked_from_server){
                hold.mLike?.setImageResource(R.drawable.ic_liked)

            }
            else{
                hold.mLike?.setImageResource(R.drawable.ic_like);
            }

            hold.mComment?.setOnClickListener {
                fragment.openCommentSheet(list.get(position).post_feed_id.toInt(),
                    list.get(position).post_likes.toInt(),
                    list.get(position).post_comments.toInt())
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

    fun like(userId: String?, feedId:String){

        val headMap: MutableMap<String, String> = HashMap()
        headMap["Content-Type"] = "application/json"

        disposable = wikiApiServe.actionLike(headMap,userId,feedId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->

                    Toast.makeText(mContext,result.status,Toast.LENGTH_SHORT).show()

                },
                { error ->
                    Toast.makeText(mContext,error.message,Toast.LENGTH_SHORT).show()

                }
            )
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
    interface clickAction {
        fun openCommentSheet(feedId: Int, likesCount:Int , commentsCount: Int)
    }

}