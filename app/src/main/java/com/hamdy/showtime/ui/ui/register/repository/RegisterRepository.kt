package com.hamdy.showtime.ui.ui.register.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RegisterRepository {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    suspend fun register(name:String,email:String, password:String):String{
        val result= FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).await()
        return if(result.user!=null) {
            val collectionReference = db.collection("Users").document(result.user!!.uid)
            val map=HashMap<String,String>()
            map["userName"]=name
            map["userId"]=result.user!!.uid
            collectionReference.set(map).await()
            "Successful"
        }else{
            "Failed"
        }
    }
}