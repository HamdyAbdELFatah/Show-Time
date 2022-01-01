package com.hamdy.showtime.ui.ui.person_details.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hamdy.showtime.ui.model.*
import com.hamdy.showtime.ui.network.PersonsService
import com.hamdy.showtime.ui.network.RetrofitClient
import com.hamdy.showtime.ui.util.API_KEY
import kotlinx.coroutines.tasks.await

class PersonDetailsRepository {

    private val TAG = "PersonDetailsRepository"
    private val auth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun getPersonDetails(id: Int): PersonDetailsResponse {
        val client = RetrofitClient.getInstance().create(PersonsService::class.java)
            .getPersonDetails(id, API_KEY)
        return client.body()!!
    }

    suspend fun getPersonImage(id: Int): List<ProfilesItem?>? {
        val client = RetrofitClient.getInstance().create(PersonsService::class.java)
            .getPersonImage(id, API_KEY)
        return client.body()?.profiles
    }

    suspend fun getPersonKnownMovies(id: String?): List<SearchKnownForItem?>? {
        val client = RetrofitClient.getInstance().create(PersonsService::class.java)
            .getPersonKnownMovies(id, API_KEY, "imdb_id")
        return client.body()?.personResults!![0]?.knownFor
    }

    suspend fun getFavorite(id: Int): Boolean {
        val collectionReference =
            db.collection("Users").document(auth.currentUser?.uid.toString())
                .collection("FavoritePersons").document(id.toString())
        val result = collectionReference.get().await()
        return result.exists()
    }

    suspend fun setFavorite(id: Int, poster: String, exist: Boolean) {
        if (!exist) {
            val collectionReference =
                db.collection("Users").document(auth.currentUser?.uid.toString())
                    .collection("FavoritePersons").document(id.toString())
            val map = HashMap<String, String>()
            map["poster"] = poster
            map["favoriteId"] = id.toString()
            collectionReference.set(map).await()
        } else {
            val collectionReference =
                db.collection("Users").document(auth.currentUser?.uid.toString())
                    .collection("FavoritePersons").document(id.toString())
            collectionReference.delete().await()
        }
    }

}