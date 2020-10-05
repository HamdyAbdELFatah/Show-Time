package com.hamdy.showtime.ui.network

import com.hamdy.showtime.ui.model.CastResponse
import com.hamdy.showtime.ui.model.MoviesDetailsResponse
import com.hamdy.showtime.ui.model.PopularResponse
import com.hamdy.showtime.ui.model.TrailerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/popular?")
    suspend fun getPopular(@Query("api_key") key:String,@Query("page") page:Int): Response<PopularResponse>

    @GET("movie/top_rated?")
    suspend fun getTopRated(@Query("api_key") key:String,@Query("page") page:Int): Response<PopularResponse>

    @GET("movie/upcoming?")
    suspend fun getUpComing(@Query("api_key") key:String,@Query("page") page:Int): Response<PopularResponse>

    @GET("trending/movie/day?")
    suspend fun getTrending(@Query("api_key") key:String,@Query("page") page:Int): Response<PopularResponse>

    @GET("movie/{id}/credits?")
    suspend fun getCastMovieList(@Path("id") page:Int,@Query("api_key") key:String): Response<CastResponse>

    @GET("movie/{id}}/videos?")
    suspend fun getTrailer(@Path("id") page:Int,@Query("api_key") key:String): Response<TrailerResponse>

    @GET("movie/{id}?")
    suspend fun getMoviesDetails(@Path("id") page:Int,@Query("api_key") key:String): Response<MoviesDetailsResponse>

}