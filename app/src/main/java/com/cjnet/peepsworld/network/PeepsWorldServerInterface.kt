package com.cjnet.peepsworld.network

import com.cjnet.peepsworld.models.AllFeedsResponse
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.HeaderMap


interface PeepsWorldServerInterface {

    @GET("all_feeds.php")
    fun getAllFeeds(@HeaderMap header: Map<String, String>):
            Observable<AllFeedsResponse>

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