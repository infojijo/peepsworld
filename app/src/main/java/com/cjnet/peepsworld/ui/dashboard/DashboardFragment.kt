package com.cjnet.peepsworld.ui.dashboard

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
import com.cjnet.peepsworld.models.Feed
import com.cjnet.peepsworld.network.PeepsWorldServerInterface
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

    private fun beginFetch() {

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

                    arrangeFeed(result)
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

    fun arrangeFeed(feedResponse: AllFeedsResponse) {

        feedList.add(Feed(
            "Raising Arizona is the best movie ever released in Hollywood",
            "Abraham",
            1,
            "www.google.com",
            "",
            ""
        ))

        for (i in 0..feedResponse.feeds.size - 1) {

            if (feedResponse.feeds.get(i).post.postTitle != null &&
                !feedResponse.feeds.get(i).post.postTitle.equals("")) {
                Log.v("Post Titles->", feedResponse.feeds.get(i).creator.creatorFullName+":"
                        +feedResponse.feeds.get(i).post.postTitle)

                feedList.add(
                    Feed(
                        feedResponse.feeds.get(i).post.postTitle,
                        feedResponse.feeds.get(i).creator.creatorFullName,
                        0,
                        feedResponse.feeds.get(i).post.postLink,
                        feedResponse.feeds.get(i).feedLikeCount,
                        feedResponse.feeds.get(i).feedCommentCount
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