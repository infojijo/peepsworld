package com.cjnet.peepsworld.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cjnet.peepsworld.R
import com.cjnet.peepsworld.models.userToken
import com.cjnet.peepsworld.network.PeepsWorldServerInterface
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.rilixtech.widget.countrycodepicker.CountryCodePicker
import com.rilixtech.widget.countrycodepicker.CountryCodePicker.OnCountryChangeListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.progress_layout.*


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    var disposable: Disposable? = null
    val wikiApiServe by lazy {
        PeepsWorldServerInterface.create()
    }

    private var PRIVATE_MODE = 0
    private val PREF_NAME = "user_sp"

    private fun beginFetch(token: String) {

        val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        val editor = sharedPref.edit()
        editor.putString(PREF_NAME, "John")
        editor.apply()


        progressBar_layout.setVisibility(View.VISIBLE)
        val headMap: MutableMap<String, String> = HashMap()
        headMap["Content-Type"] = "application/json"
        val userToken = userToken(token)

        disposable = wikiApiServe.login(headMap, userToken)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    progressBar_layout.setVisibility(View.INVISIBLE)
                    startActivity(Intent(this, LandingScreen::class.java))
                    this.finish()
                },
                { error ->
                    progressBar_layout.setVisibility(View.INVISIBLE)
                    Toast.makeText(
                        this,
                        "Error while login"+error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
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

    lateinit var sheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        et_token.setText("98f13708210194c475687be6106a3b84".capitalize())
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
        et_token.setText("98f13708210194c475687be6106a3b84".capitalize())
        beginFetch(et_token.text.toString())
        //

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
        this.finish()
    }
}