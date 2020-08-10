package com.cjnet.peepsworld.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cjnet.peepsworld.R
import com.cjnet.peepsworld.models.RegistrationBody
import com.cjnet.peepsworld.network.PeepsWorldServerInterface
import com.rilixtech.widget.countrycodepicker.CountryCodePicker
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.progress_layout.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegistrationActivity : AppCompatActivity(), View.OnClickListener {

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
            genderError.setVisibility(View.INVISIBLE)
            if(validation())
            signUpCall()
            else
                Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show()
        }

        phone_usa.setOnClickListener(this)
        phone_india.setOnClickListener(this)
        phone_mexico.setOnClickListener(this)
        phone_canada.setOnClickListener(this)
    }


    private fun validation(): Boolean {
        var valid: Boolean = true;

        if (editTextFname.text.toString().isEmpty()) {
            valid = false;
        } else if (editTextLname.text.toString().isEmpty()) {
            valid = false;
        } else if (editTextEmail.text.toString().isEmpty()) {
            valid = false;
        }else if(et_mobile.text.toString().isEmpty()){
            valid = false;
        }else if(et_mobile.text.length<10){
            valid = false;
            et_mobile.setError("mobile number is not valid")
        }

        else if(!emailvalidation(editTextEmail.text.toString())){
            editTextEmail.setError("please check email format")
            valid = false;
        } else if(gender().equals("N")){
            genderError.setVisibility(View.VISIBLE)
            //radio_group.setBackgroundColor(resources.getColor(R.color.colorAccent))
            valid = false;
        }
        return valid
    }

    private fun gender(): String {

        var genderString: String = "N"

        if (mr.isChecked) {
            genderString = "M"
        } else if (ms.isChecked) {
            genderString = "F"
        } else if(mx.isChecked){
            genderString = "X"
        }

        return genderString
    }

    private fun signUpCall() {


        progressBar_layout.setVisibility(View.VISIBLE)

        val headMap: MutableMap<String, String> = HashMap();
        headMap["Content-Type"] = "application/json"
        val registrationUser = RegistrationBody(
            editTextFname.text.toString(),
            editTextLname.text.toString(),
            editTextEmail.text.toString(),
            et_mobile.text.toString(),
            gender()
        )

        disposable = wikiApiServe.registration(headMap, registrationUser)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    progressBar_layout.setVisibility(View.INVISIBLE)
                    if (result.success == "201") {
                        startActivity(Intent(this, LandingScreen::class.java))
                    }
                },
                { error ->
                    progressBar_layout.setVisibility(View.INVISIBLE)
                    Toast.makeText(
                        this,
                        "Error while registering" + error.message,
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

    private fun emailvalidation(email:String):Boolean{

        var EMAIL_REGEX  = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        var pattern: Pattern;
        var matcher: Matcher;

        pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(email);
        return matcher.matches();
        }

}
