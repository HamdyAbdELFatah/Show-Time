package com.hamdy.showtime.ui.ui.login.repository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class LoginRepository {
    suspend fun login(email:String,password:String):String{
        val result=FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).await()
        return if(result.user!=null) {
            "Successful"
        }else{
            "Failed"
        }
    }

}