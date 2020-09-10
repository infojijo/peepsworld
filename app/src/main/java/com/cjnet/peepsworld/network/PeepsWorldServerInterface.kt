package com.cjnet.peepsworld.network

import com.cjnet.peepsworld.models.*
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


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

    @GET("user_like_details.php")
    fun userLikes(@HeaderMap header: Map<String, String>, @Query("userID") userID: String):
            Observable<AllLikes>
    @GET("user_feed_action_like.php")
    fun actionLike(@HeaderMap header: Map<String, String>,
                   @Query("userID") userID: String?,
                   @Query("feedID") feedID: String):
            Observable<ActionLikeResponse>

    @GET("feed_like_count.php")
    fun likeCounts(@HeaderMap header: Map<String, String>):
            Observable<AllLikeCount>



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