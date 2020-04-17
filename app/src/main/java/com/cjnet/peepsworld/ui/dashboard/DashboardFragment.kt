package com.cjnet.peepsworld.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.cjnet.peepsworld.R
import com.cjnet.peepsworld.models.Feed
import com.cjnet.peepsworld.ui.adapter.ListAdapter
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

    private lateinit var menuViewModel: DashboardViewModel

    private val sampleFeedsList = listOf(
        Feed("Raising Arizona is the best movie ever released in Hollywood",
            "Abraham",
            1,
            "www.google.com"),
        Feed("The charges against Raising Arizona made the movie a blockbuster",
            "Adam John",
            0
            ,"www.google.com"),
        Feed("Vampire's Kiss is the best movie ever released in Hollywood",
            "Elyce Pyrie",
            2,
            "https://www.youtube.com/watch?v=EIViC-TCP3g"),
        Feed("Con Air was one of the heck movie",
            "Jose Alexander",
            0,
            "www.google.com"),
        Feed("Gone in 60 Seconds was just good for 60 seconds",
            "Nicolas Cage",
            0,
            "www.google.com"),
        Feed("ഏവർക്കും എന്റെ ഹൃദയം നിറഞ്ഞ വിഷു ആശംസകൾ",
            "അനിത വർമ്മ ",
            0,
            "www.google.com"),
        Feed("The Wicker Man was movie about a wicked man",
            "John Janice",
            0,
            "www.google.com"),
        Feed("Ghost Rider rides well than normal riders",
            "Gane Sander",
            0,
            "www.google.com"),
        Feed(
            "Knowing that this is just a simple text was an amazing discovery infact a great one!",
            "Seetha Devi",
            2,
            "https://www.freepik.com/"
        )
    )

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

        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ListAdapter(sampleFeedsList, context)
        }
    }
}