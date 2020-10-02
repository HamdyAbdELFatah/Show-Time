package com.hamdy.showtime.ui.ui.home.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.hamdy.showtime.ui.network.MoviesService
import com.hamdy.showtime.ui.network.RetrofitClient
import com.hamdy.showtime.ui.ui.home.model.PopularResultsItem
import com.hamdy.showtime.ui.util.API_KEY

class HomeRepository {

   suspend fun getPopular():List<PopularResultsItem>? {
            val client= RetrofitClient.getInstance().create(MoviesService::class.java).getPopular(API_KEY)
            if(client.isSuccessful){
                Log.d("HomeViewModel",client.body().toString())
            }else{
                Log.e("HomeViewModel",client.message())
            }
       return client.body()?.results
    }

   suspend fun getTopRated():List<PopularResultsItem>? {
            val client= RetrofitClient.getInstance().create(MoviesService::class.java).getTopRated(API_KEY)
            if(client.isSuccessful){
                Log.d("HomeViewModel",client.body().toString())
            }else{
                Log.e("HomeViewModel",client.message())
            }
       return client.body()?.results
    }

   suspend fun getUpComing():List<PopularResultsItem>? {
            val client= RetrofitClient.getInstance().create(MoviesService::class.java).getUpComing(API_KEY)
            if(client.isSuccessful){
                Log.d("HomeViewModel",client.body().toString())
            }else{
                Log.e("HomeViewModel",client.message())
            }
       return client.body()?.results
    }

    suspend fun getTrending():List<PopularResultsItem>? {
            val client= RetrofitClient.getInstance().create(MoviesService::class.java).getTrending(API_KEY)
            if(client.isSuccessful){
                Log.d("HomeViewModel",client.body().toString())
            }else{
                Log.e("HomeViewModel",client.message())
            }
       return client.body()?.results
    }


}