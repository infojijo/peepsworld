package com.cjnet.peepsworld.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.cjnet.peepsworld.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.rilixtech.widget.countrycodepicker.CountryCodePicker
import com.rilixtech.widget.countrycodepicker.CountryCodePicker.OnCountryChangeListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet.*


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when (v?.getId()) {
            R.id.phone_india -> {
                editTextPhoneCode.setText("+91 ")
                phonecode_layout.setVisibility(View.GONE)
            }
            R.id.phone_usa -> {
                editTextPhoneCode.setText("+1 ")
                phonecode_layout.setVisibility(View.GONE)
            }
            R.id.phone_mexico -> {
                editTextPhoneCode.setText("+52 ")
                phonecode_layout.setVisibility(View.GONE)
            }
            R.id.phone_canada -> {
                editTextPhoneCode.setText("+1 ")
                phonecode_layout.setVisibility(View.GONE)
            }
        }

    }

    lateinit var sheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_navigate_reg.setOnClickListener { callRegistrationActivity() }
        editTextPhoneCode.setOnClickListener {
            phonecode_layout.setVisibility(View.VISIBLE)
        }
        txtSubmit.setOnClickListener {
            callBottomSheet()
        }

        phone_usa.setOnClickListener(this)
        phone_india.setOnClickListener(this)
        phone_mexico.setOnClickListener(this)
        phone_canada.setOnClickListener(this)
    }


    private fun callBottomSheet() {

        startActivity(Intent(this, LandingScreen::class.java))

    }

    private fun openccp() {

        var concpp: CountryCodePicker = CountryCodePicker(this)
        concpp.showCountryCodePickerDialog()
        concpp.setOnCountryChangeListener(OnCountryChangeListener { selectedCountry ->
            editTextPhoneCode.setText("+" + selectedCountry.phoneCode)
        })
    }

    fun callRegistrationActivity() {
        startActivity(Intent(this, RegistrationActivity::class.java))
        finish()
    }
}