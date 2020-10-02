package com.hamdy.showtime.ui.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient {
    companion object {
        private var INSTANCE: Retrofit? = null
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        fun getInstance() :Retrofit{
            if (INSTANCE == null) {
                 INSTANCE = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                     .build()
            }
            return INSTANCE!!
        }
    }
}