package com.example.codingexcercise.model.network

import com.example.codingexcercise.model.data.MusicResponse
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicAPI {

    @GET("search?")
    fun getMeSongs(
            @Query("term") term: String,

            ): Single<MusicResponse>


    companion object {

        fun initRetrofit(): MusicAPI {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://itunes.apple.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(OkHttpClient.Builder().apply {
                        addInterceptor(
                                HttpLoggingInterceptor()
                                        .apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
                    }.build())
                    .build()

            val api = retrofit.create(MusicAPI::class.java)
            return api
        }
    }


}