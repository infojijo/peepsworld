package com.cjnet.peepsworld.ui.menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cjnet.peepsworld.R
import com.cjnet.peepsworld.ui.LoginActivity
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment() {

    private lateinit var menuViewModel: MenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuViewModel =
            ViewModelProviders.of(this).get(MenuViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_menu,
            container,
            false)
        val textView: TextView = root.findViewById(R.id.text_menu)
        menuViewModel.text.observe(this, Observer {
            textView.text = it
            logout.setOnClickListener {
                activity?.finish()
                startActivity(Intent(activity, LoginActivity::class.java))

            }
        })

        return root
    }
}