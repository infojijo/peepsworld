package com.cjnet.peepsworld.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

    private fun saveUser(email: String){

        val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        val editor = sharedPref.edit()
        editor.putString(PREF_NAME, email)
        editor.apply()
    }

    private fun beginFetch(token: String) {



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
                    if(result.success.equals("200"))
                    {
                        saveUser(result.userEmail)
                        startActivity(Intent(this, LandingScreen::class.java))

                    this.finish()
                    }
                    else{
                        Toast.makeText(
                            this,
                            "Error while login",
                            Toast.LENGTH_SHORT).show()
                    }

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
        //et_token.setText("98f13708210194c475687be6106a3b84".capitalize())
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

        val window = this.getWindow()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.color_splash))
    }


    private fun callBottomSheet() {
        //et_token.setText("98f13708210194c475687be6106a3b84".capitalize())
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