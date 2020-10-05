package com.hamdy.showtime.ui.ui.movies_details.repository

import com.hamdy.showtime.ui.model.CastItem
import com.hamdy.showtime.ui.model.CastResponse
import com.hamdy.showtime.ui.model.MoviesDetailsResponse
import com.hamdy.showtime.ui.network.MoviesService
import com.hamdy.showtime.ui.network.RetrofitClient
import com.hamdy.showtime.ui.model.PopularResultsItem
import com.hamdy.showtime.ui.util.API_KEY

class MoviesDetailsRepository {

    private val TAG = "MoviesDetailsRepository"

    suspend fun getCastMovieList(id:Int): List<CastItem?>? {
        val client= RetrofitClient.getInstance().create(MoviesService::class.java).getCastMovieList(id,API_KEY)
        return client.body()?.cast
    }

    suspend fun getMoviesDetails(id:Int): MoviesDetailsResponse {
        val client= RetrofitClient.getInstance().create(MoviesService::class.java).getMoviesDetails(id,API_KEY)
        return client.body()!!
    }




}