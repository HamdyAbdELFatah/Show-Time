package com.hamdy.showtime.ui.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object AoiClient {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}