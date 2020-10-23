package com.hamdy.showtime.ui.ui.movies_details.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hamdy.showtime.ui.model.CastItem
import com.hamdy.showtime.ui.model.CastResponse
import com.hamdy.showtime.ui.model.MoviesDetailsResponse
import com.hamdy.showtime.ui.network.MoviesService
import com.hamdy.showtime.ui.network.RetrofitClient
import com.hamdy.showtime.ui.model.PopularResultsItem
import com.hamdy.showtime.ui.util.API_KEY
import kotlinx.coroutines.tasks.await

class MoviesDetailsRepository {
    private val TAG = "MoviesDetailsRepository"
    private val auth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    suspend fun getCastMovieList(id:Int): List<CastItem?>? {
        val client= RetrofitClient.getInstance().create(MoviesService::class.java).getCastMovieList(id,API_KEY)
        return client.body()?.cast
    }

    suspend fun getMoviesDetails(id:Int): MoviesDetailsResponse {
        val client= RetrofitClient.getInstance().create(MoviesService::class.java).getMoviesDetails(id,API_KEY)
        return client.body()!!
    }

    suspend fun getFavorite(id:Int):Boolean {
        val collectionReference =
            db.collection("Users").document(auth.currentUser?.uid.toString())
                .collection("FavoriteMovies").document(id.toString())
        val result=collectionReference.get().await()
        return result.exists()
    }
    suspend fun setFavorite(id:Int,poster:String,exist:Boolean) {
        if(!exist) {
            val collectionReference =
                db.collection("Users").document(auth.currentUser?.uid.toString())
                    .collection("FavoriteMovies").document(id.toString())
            val map = HashMap<String, String>()
            map["poster"] = poster
            map["favoriteId"] = id.toString()
            collectionReference.set(map).await()
        }else{
            val collectionReference =
                db.collection("Users").document(auth.currentUser?.uid.toString())
                    .collection("FavoriteMovies").document(id.toString())
            collectionReference.delete().await()
        }
    }

}