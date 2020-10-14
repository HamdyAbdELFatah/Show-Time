package com.hamdy.showtime.ui.ui.person_details.repository

import com.hamdy.showtime.ui.model.CastItem
import com.hamdy.showtime.ui.model.MoviesDetailsResponse
import com.hamdy.showtime.ui.model.PersonDetailsResponse
import com.hamdy.showtime.ui.model.ProfilesItem
import com.hamdy.showtime.ui.network.MoviesService
import com.hamdy.showtime.ui.network.PersonsService
import com.hamdy.showtime.ui.network.RetrofitClient
import com.hamdy.showtime.ui.util.API_KEY

class PersonDetailsRepository {

    private val TAG = "PersonDetailsRepository"

    suspend fun getPersonDetails(id:Int): PersonDetailsResponse {
        val client= RetrofitClient.getInstance().create(PersonsService::class.java).getPersonDetails(id,API_KEY)
        return client.body()!!
    }
    suspend fun getPersonImage(id:Int): List<ProfilesItem?>? {
        val client= RetrofitClient.getInstance().create(PersonsService::class.java).getPersonImage(id,API_KEY)
        return client.body()?.profiles
    }



}