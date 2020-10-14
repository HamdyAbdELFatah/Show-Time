package com.hamdy.showtime.ui.ui.person_list.repository

import com.hamdy.showtime.ui.model.PersonsResponse
import com.hamdy.showtime.ui.network.MoviesService
import com.hamdy.showtime.ui.network.RetrofitClient
import com.hamdy.showtime.ui.model.PopularResponse
import com.hamdy.showtime.ui.network.PersonsService
import com.hamdy.showtime.ui.util.API_KEY

class PersonsRepository {

   private  val TAG = "PersonsRepository"
    suspend fun getPopular(page: Int): PersonsResponse? {
        val client= RetrofitClient.getInstance().create(PersonsService::class.java).getPopular(API_KEY,page)
        return client.body()
    }
}