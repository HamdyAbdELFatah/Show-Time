package com.hamdy.showtime.ui.ui.person_details.repository

import com.hamdy.showtime.ui.model.*
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

    suspend fun getPersonKnownMovies(id: String?): List<KnownForItem?>? {
        val client= RetrofitClient.getInstance().create(PersonsService::class.java).getPersonKnownMovies(id,API_KEY,"imdb_id")
        return client.body()?.personResults!![0]?.knownFor
    }



}