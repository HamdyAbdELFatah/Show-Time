package com.hamdy.showtime.ui.ui.all_movies.repository

import com.hamdy.showtime.ui.network.MoviesService
import com.hamdy.showtime.ui.network.RetrofitClient
import com.hamdy.showtime.ui.model.PopularResponse
import com.hamdy.showtime.ui.util.API_KEY

class AllMoviesRepository {

   private  val TAG = "AllMoviesRepository"
    suspend fun getPopular(page: Int): PopularResponse? {
        val client= RetrofitClient.getInstance().create(MoviesService::class.java).getPopular(API_KEY,page)
        return client.body()
    }

    suspend fun getTopRated(page: Int): PopularResponse? {
        val client= RetrofitClient.getInstance().create(MoviesService::class.java).getTopRated(API_KEY,page)
        return client.body()
    }

    suspend fun getUpComing(page: Int): PopularResponse? {
        val client= RetrofitClient.getInstance().create(MoviesService::class.java).getUpComing(API_KEY,page)
        return client.body()
    }

    suspend fun getTrending(page: Int): PopularResponse? {
        val client = RetrofitClient.getInstance().create(MoviesService::class.java).getTrending(API_KEY, page)
        return client.body()
    }
}