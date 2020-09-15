package com.cjnet.peepsworld.ui.dashboard

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.cjnet.peepsworld.R
import com.cjnet.peepsworld.models.AllFeedsResponse
import com.cjnet.peepsworld.models.AllLikeCount
import com.cjnet.peepsworld.models.Feed
import com.cjnet.peepsworld.network.PeepsWorldServerInterface
import com.cjnet.peepsworld.ui.adapter.ListAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.layout_bottom_sheet_comments.*
import kotlinx.android.synthetic.main.progress_layout.*


class DashboardFragment : Fragment(), ListAdapter.clickAction {

    private lateinit var menuViewModel: DashboardViewModel
    val wikiApiServe by lazy {
        PeepsWorldServerInterface.create()
    }
    var disposable: Disposable? = null
    val feedList = ArrayList<Feed>()
    private var PRIVATE_MODE = 0
    private val PREF_NAME = "user_likes"
    var likesString: String? = ""
    var scrollLock: Boolean = false

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>


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
            adapter = ListAdapter(feedList, context,this@DashboardFragment)
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

        recycler_view.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, m: MotionEvent): Boolean {

                if (bottomSheetBehavior.state == STATE_EXPANDED) {
                    return scrollLock
                }
                else {
                    return scrollLock
                }
            }
        })


        /* recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
             override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                 super.onScrollStateChanged(recyclerView, newState)

             }
         })*/



        bottomSheetBehavior = from(bottomSheet)
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    STATE_COLLAPSED ->{ //Toast.makeText(activity, "STATE_COLLAPSED", Toast.LENGTH_SHORT).show()
                        scrollLock = false}
                    STATE_EXPANDED ->{ //Toast.makeText(activity, "STATE_EXPANDED", Toast.LENGTH_SHORT).show()
                        scrollLock = true}
                  /*  STATE_DRAGGING -> Toast.makeText(activity, "STATE_DRAGGING", Toast.LENGTH_SHORT).show()
                    STATE_SETTLING -> Toast.makeText(activity, "STATE_SETTLING", Toast.LENGTH_SHORT).show()
                    STATE_HIDDEN -> Toast.makeText(activity, "STATE_HIDDEN", Toast.LENGTH_SHORT).show()*/
                    else -> {}//Toast.makeText(activity, "OTHER_STATE", Toast.LENGTH_SHORT).show()
                }}
        })
        beginFetch()
    }

    override fun openCommentSheet(feedId: Int, likes:Int, comment:Int) {
        Toast.makeText(activity,"Feed Id ->"+feedId,Toast.LENGTH_LONG).show()
        bottom_sheet_like_count.setText(" "+likes)
        bottom_sheet_comment_count.setText(" "+comment)
        if (bottomSheetBehavior.state == STATE_EXPANDED){
            bottomSheetBehavior.state = STATE_COLLAPSED

        }
        else{
            bottomSheetBehavior.state = STATE_EXPANDED

        }


    }
}
