package com.cjnet.peepsworld.ui.dashboard

import android.os.Bundle
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
        Feed("Raising Arizona", "Jijo",1),
        Feed("Raising Arizona", "Jijo",0),
        Feed("Vampire's Kiss", "Elyce",0),
        Feed("Con Air", "Jose",0),
        Feed("Gone in 60 Seconds", "Joseph",0),
        Feed("National Treasure", "Jijo",0),
        Feed("The Wicker Man", "Nico",0),
        Feed("Ghost Rider", "Game",0),
        Feed("Knowing", "Seeth",0)
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
            adapter = ListAdapter(sampleFeedsList)
        }
    }
}