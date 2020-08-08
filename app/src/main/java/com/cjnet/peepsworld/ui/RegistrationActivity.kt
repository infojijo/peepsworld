package com.cjnet.peepsworld.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cjnet.peepsworld.R
import com.cjnet.peepsworld.models.RegistrationBody
import com.cjnet.peepsworld.models.userToken
import com.cjnet.peepsworld.network.PeepsWorldServerInterface
import com.rilixtech.widget.countrycodepicker.CountryCodePicker
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.progress_layout.*

class RegistrationActivity : AppCompatActivity() , View.OnClickListener {

    var disposable: Disposable? = null
    val wikiApiServe by lazy {
        PeepsWorldServerInterface.create()
    }


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
        txt_registration.setOnClickListener {
            signInCall()
        }

        phone_usa.setOnClickListener(this)
        phone_india.setOnClickListener(this)
        phone_mexico.setOnClickListener(this)
        phone_canada.setOnClickListener(this)
    }

    private fun signInCall() {


        progressBar_layout.setVisibility(View.VISIBLE)

        val headMap: MutableMap<String, String> = HashMap()
        headMap["Content-Type"] = "application/json"
        val registrationUser = RegistrationBody(
            editTextFname.text.toString(),
            editTextLname.text.toString(),
            editTextEmail.text.toString(),
            et_mobile.text.toString(),
            "M"
        )

        disposable = wikiApiServe.registration(headMap, registrationUser)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    progressBar_layout.setVisibility(View.INVISIBLE)
                    startActivity(Intent(this, LandingScreen::class.java))
                },
                { error ->
                    progressBar_layout.setVisibility(View.INVISIBLE)
                    Toast.makeText(
                        this,
                        "Error while registering"+error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )

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
