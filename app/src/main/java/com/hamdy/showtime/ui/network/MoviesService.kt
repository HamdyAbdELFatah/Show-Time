package com.hamdy.showtime.ui.network

import com.hamdy.showtime.ui.ui.home.model.PopularResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/popular?")
    suspend fun getPopular(@Query("api_key") key:String): Response<PopularResponse>

    @GET("movie/top_rated?")
    suspend fun getTopRated(@Query("api_key") key:String): Response<PopularResponse>

    @GET("movie/upcoming?")
    suspend fun getUpComing(@Query("api_key") key:String): Response<PopularResponse>

    @GET("trending/movie/day?")
    suspend fun getTrending(@Query("api_key") key:String): Response<PopularResponse>

}