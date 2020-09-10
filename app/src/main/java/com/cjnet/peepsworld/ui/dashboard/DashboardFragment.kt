package com.cjnet.peepsworld.ui.dashboard

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.cjnet.peepsworld.R
import com.cjnet.peepsworld.models.AllFeedsResponse
import com.cjnet.peepsworld.models.AllLikeCount
import com.cjnet.peepsworld.models.Feed
import com.cjnet.peepsworld.network.PeepsWorldServerInterface
import com.cjnet.peepsworld.ui.LandingScreen
import com.cjnet.peepsworld.ui.adapter.FeedAdapter
import com.cjnet.peepsworld.ui.adapter.ListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.progress_layout.*


class DashboardFragment : Fragment() {

    private lateinit var menuViewModel: DashboardViewModel

    val wikiApiServe by lazy {
        PeepsWorldServerInterface.create()
    }
    var disposable: Disposable? = null

    val feedList = ArrayList<Feed>()

    private var PRIVATE_MODE = 0
    private val PREF_NAME = "user_likes"
    var likesString: String? = ""

    fun callLikesCount(feedResponse: AllFeedsResponse){

        val headMap: MutableMap<String, String> = HashMap()
        headMap["Content-Type"] = "application/json"
        disposable = wikiApiServe.likeCounts(headMap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                arrangeFeed(feedResponse,result)
                //Toast.makeText(applicationContext,"Feeds and Likes->"+result.feeds.size, Toast.LENGTH_SHORT).show()
            },
                { error ->
                    Log.w("Peeps",error.message)
                })

    }

    private fun beginFetch() {


        val sharedPref: SharedPreferences? = activity?.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        likesString = sharedPref?.getString(PREF_NAME,"9")

        progressBar_layout.setVisibility(View.VISIBLE)
        val headMap: MutableMap<String, String> = HashMap()
        headMap["Content-Type"] = "application/json"

        disposable = wikiApiServe.getAllFeeds(headMap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    progressBar_layout.setVisibility(View.INVISIBLE)
                    Toast.makeText(
                        activity,
                        "Results Fetched Successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    callLikesCount(result)
                },
                { error ->
                    progressBar_layout.setVisibility(View.INVISIBLE)
                    Toast.makeText(
                        activity,
                        error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
    }

    fun arrangeFeed(feedResponse: AllFeedsResponse, likeCount: AllLikeCount) {

        feedList.add(Feed(
            "",
            "",
            "",
            1,
            "",
            "",
            "",
            false
        ))

        for (i in 0..feedResponse.feeds.size - 1) {

            var countLike:String = "0"
            for(j in 0..likeCount.feeds.size-1){
                if(likeCount.feeds.get(j).FeedId.equals(feedResponse.feeds.get(i).feedID)){
                    countLike = likeCount.feeds.get(j).LikeCount
                }
            }


            if (feedResponse.feeds.get(i).post.postTitle != null &&
                !feedResponse.feeds.get(i).post.postTitle.equals("")) {
                Log.v("Post Titles->", feedResponse.feeds.get(i).creator.creatorFullName+":"
                        +feedResponse.feeds.get(i).post.postTitle)


                val feedId:String = feedResponse.feeds?.get(i)?.feedID
                var liked:Boolean = false
                if(likesString?.contains(feedId)?:false){
                    liked = true
                }

                feedList.add(
                    Feed(
                        feedResponse.feeds.get(i).feedID,
                        feedResponse.feeds.get(i).post.postTitle,
                        feedResponse.feeds.get(i).creator.creatorFullName,
                        0,
                        feedResponse.feeds.get(i).post.postLink,
                        countLike,
                        feedResponse.feeds.get(i).feedCommentCount,
                        liked
                    )
                )
            }
        }
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ListAdapter(feedList, context)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(
            R.layout.fragment_dashboard,
            container,
            false
        )
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        menuViewModel.text.observe(this, Observer {
            textView.text = it
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        beginFetch()

    }
}