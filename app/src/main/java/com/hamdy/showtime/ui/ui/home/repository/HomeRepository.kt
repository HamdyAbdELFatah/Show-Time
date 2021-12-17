package com.hamdy.showtime.ui.ui.home.repository

import com.hamdy.showtime.ui.network.MoviesService
import com.hamdy.showtime.ui.network.RetrofitClient
import com.hamdy.showtime.ui.model.PopularResultsItem
import com.hamdy.showtime.ui.util.API_KEY

class HomeRepository {

    private val TAG = "HomeRepository"
    suspend fun getPopular(): List<PopularResultsItem>? {
        val client =
            RetrofitClient.getInstance().create(MoviesService::class.java).getPopular(API_KEY, 1)
        return client.body()?.results
    }

    suspend fun getTopRated(): List<PopularResultsItem>? {
        val client =
            RetrofitClient.getInstance().create(MoviesService::class.java).getTopRated(API_KEY, 1)
        return client.body()?.results
    }

    suspend fun getUpComing(): List<PopularResultsItem>? {
        val client =
            RetrofitClient.getInstance().create(MoviesService::class.java).getUpComing(API_KEY, 1)
        return client.body()?.results
    }

    suspend fun getTrending(): List<PopularResultsItem>? {
        val client =
            RetrofitClient.getInstance().create(MoviesService::class.java).getTrending(API_KEY, 1)
        return client.body()?.results
    }


}