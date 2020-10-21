package com.hamdy.showtime.ui.ui.login.repository
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class LoginRepository {
    private val TAG = "LoginRepository"
    suspend fun login(email:String,password:String):String{

        return try {
            val result=FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).await()
            if(result.user !=null) {
                Log.d(TAG, "login: if")
                "Successful"
            }else{
                Log.d(TAG, "login: else")
                "Failed"
            }
        }catch (e:Exception){
            "Failed"
        }

    }

}