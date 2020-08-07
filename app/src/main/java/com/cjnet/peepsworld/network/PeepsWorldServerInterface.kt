package com.cjnet.peepsworld.network

import com.cjnet.peepsworld.models.AllFeedsResponse
import com.cjnet.peepsworld.models.LoginResponse
import com.cjnet.peepsworld.models.RegistrationBody
import com.cjnet.peepsworld.models.userToken
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST


interface PeepsWorldServerInterface {

    @GET("all_feeds.php")
    fun getAllFeeds(@HeaderMap header: Map<String, String>):
            Observable<AllFeedsResponse>


    @POST("login.php")
    fun login(@HeaderMap header: Map<String, String>, @Body user: userToken):
            Observable<LoginResponse>

    @POST("registration.php")
    fun registration(@HeaderMap header: Map<String, String>, @Body user: RegistrationBody):
            Observable<LoginResponse>

    companion object {
        fun create(): PeepsWorldServerInterface {

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://peepsworld.in/ws/")
                .build()

            return retrofit.create(PeepsWorldServerInterface::class.java)
        }
    }
}