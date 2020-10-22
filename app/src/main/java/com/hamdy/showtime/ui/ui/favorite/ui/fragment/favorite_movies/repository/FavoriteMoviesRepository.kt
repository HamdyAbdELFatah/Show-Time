package com.hamdy.showtime.ui.ui.favorite.ui.fragment.favorite_movies.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FavoriteMoviesRepository {
    private val auth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    suspend fun getFavorite(id:Int):Boolean {
        val collectionReference =
            db.collection("Users").document(auth.currentUser?.uid.toString())
                .collection("FavoriteMovies").document(id.toString())
        val result=collectionReference.get().await()
        return result.exists()
    }
}