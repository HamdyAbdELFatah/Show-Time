package com.hamdy.showtime.ui.ui.search.fragment.movie_search.repository

import com.hamdy.showtime.ui.model.SearchMoviesResponse
import com.hamdy.showtime.ui.model.SearchPersonResponse
import com.hamdy.showtime.ui.network.MoviesService
import com.hamdy.showtime.ui.network.RetrofitClient
import com.hamdy.showtime.ui.util.API_KEY

class SearchPersonRepository {
    suspend fun getSearchPerson(query:String): SearchPersonResponse {
        val client= RetrofitClient.getInstance().create(MoviesService::class.java).getPersonsSearch(
            API_KEY,query)

        return client.body()!!
    }
}