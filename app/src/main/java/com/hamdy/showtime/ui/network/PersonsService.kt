package com.hamdy.showtime.ui.network

import com.hamdy.showtime.ui.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonsService {

    @GET("person/popular?")
    suspend fun getPopular(@Query("api_key") key:String,@Query("page") page:Int): Response<PersonsResponse>

    @GET("person/{id}?")
    suspend fun getPersonDetails(@Path("id") page:Int,@Query("api_key") key:String): Response<PersonDetailsResponse>

    @GET("person/{id}/images?")
    suspend fun getPersonImage(@Path("id") page:Int,@Query("api_key") key:String): Response<PersonImageResponse>

    @GET("find/{id}?")
    suspend fun getPersonKnownMovies(@Path("id") page: String?, @Query("api_key") key:String,
                                     @Query("external_source") external_source:String): Response<FindPersonResponse>



}