package com.cjnet.peepsworld.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cjnet.peepsworld.R
import com.rilixtech.widget.countrycodepicker.CountryCodePicker
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.bottom_sheet.*

class RegistrationActivity : AppCompatActivity() , View.OnClickListener {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        tv_navigate_login.setOnClickListener { navigateToLogin() }
        img_back.setOnClickListener { navigateToLogin() }
       /* editTextPhoneCode.setOnClickListener {
            openccp()
        }*/

        editTextPhoneCode.setOnClickListener {
            phonecode_layout.setVisibility(View.VISIBLE)
        }

        phone_usa.setOnClickListener(this)
        phone_india.setOnClickListener(this)
        phone_mexico.setOnClickListener(this)
        phone_canada.setOnClickListener(this)
    }

    private fun openccp() {

        var concpp: CountryCodePicker = CountryCodePicker(this)
        concpp.showCountryCodePickerDialog()
        concpp.setOnCountryChangeListener(CountryCodePicker.OnCountryChangeListener { selectedCountry ->
            editTextPhoneCode.setText("+" + selectedCountry.phoneCode)
        })
    }

    fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
