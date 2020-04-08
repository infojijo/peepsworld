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
        Feed("Raising Arizona", "Jijo"),
        Feed("Vampire's Kiss", "Elyce"),
        Feed("Con Air", "Jose"),
        Feed("Gone in 60 Seconds", "Joseph"),
        Feed("National Treasure", "Jijo"),
        Feed("The Wicker Man", "Nico"),
        Feed("Ghost Rider", "Game"),
        Feed("Knowing", "Seeth")
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