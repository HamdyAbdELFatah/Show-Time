package com.hamdy.showtime.ui.ui.search.fragment.movie_search.repository

import com.hamdy.showtime.ui.model.SearchMoviesResponse
import com.hamdy.showtime.ui.network.MoviesService
import com.hamdy.showtime.ui.network.RetrofitClient
import com.hamdy.showtime.ui.ui.favorite.model.FavoriteItem
import com.hamdy.showtime.ui.util.API_KEY

class SearchMoviesRepository {
    suspend fun getSearchMovies(query:String):SearchMoviesResponse {
        val client= RetrofitClient.getInstance().create(MoviesService::class.java).getMoviesSearch(
            API_KEY,query)

        return client.body()!!
    }
}