package com.hamdy.showtime.ui.network

import com.hamdy.showtime.ui.model.*
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
    suspend fun getTrailer(@Path("id") id:Int,@Query("api_key") key:String): Response<VideoResponse>

    @GET("movie/{id}?")
    suspend fun getMoviesDetails(@Path("id") page:Int,@Query("api_key") key:String): Response<MoviesDetailsResponse>

    @GET("search/movie?")
    suspend fun getMoviesSearch(@Query("api_key") key:String,@Query("query") query:String): Response<SearchMoviesResponse>

    @GET("search/person?")
    suspend fun getPersonsSearch(@Query("api_key") key:String,@Query("query") query:String): Response<SearchPersonResponse>


}