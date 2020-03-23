package com.cjnet.peepsworld.ui

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cjnet.peepsworld.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.content_for_bottom_activity.*


class BottomSheetActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var sheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onClick(v: View?) {
        when (v?.getId()) {
            R.id.phone_india -> {
                Toast.makeText(this, "India", Toast.LENGTH_LONG).show();
                collapseBS(sheetBehavior)
            }
            R.id.phone_usa -> {
                Toast.makeText(this, "USA", Toast.LENGTH_LONG).show();
                collapseBS(sheetBehavior)
            }
            R.id.phone_canada -> {
                Toast.makeText(this, "Canada", Toast.LENGTH_LONG).show();
                collapseBS(sheetBehavior)
            }
            R.id.phone_mexico -> {
                Toast.makeText(this, "Mexico", Toast.LENGTH_LONG).show();
                collapseBS(sheetBehavior)
            }
        }
    }

    fun collapseBS(sheetBehavior: BottomSheetBehavior<LinearLayout>){
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        btn_bottom_sheet.setText("Expand sheet");
        bottom_sheet.setVisibility(View.GONE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet)

        phone_india.setOnClickListener(this)
        phone_usa.setOnClickListener(this)
        phone_canada.setOnClickListener(this)
        phone_mexico.setOnClickListener(this)
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        btn_bottom_sheet.setOnClickListener {

            if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                bottom_sheet.setVisibility(View.VISIBLE)
                btn_bottom_sheet.setText("Close sheet");
            } else {
                collapseBS(sheetBehavior)
            }
        }

        sheetBehavior.setBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(view: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        btn_bottom_sheet.text = "Close Sheet"
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        btn_bottom_sheet.text = "Expand Sheet"
                        collapseBS(sheetBehavior)
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }
            override fun onSlide(view: View, v: Float) {}
        })
    }
}
