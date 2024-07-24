package com.example.core.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.core.data.network.Result
import com.example.core.data.response.NewsResponse
import com.example.core.di.ApiContract
import kotlinx.coroutines.Dispatchers
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiContract: ApiContract
) {
    fun getNews(): LiveData<Result<NewsResponse>> = liveData(Dispatchers.IO) {
        emit(Result.Loading)
        val response = apiContract.getNews()
        val responseBody = response.body()
        try {
            if(response.isSuccessful && responseBody != null){
                emit(Result.Success(responseBody))
            }else{
                val errorBody = response.errorBody()?.string()
                val errorMessage = try{
                    JSONObject(errorBody).getString("message")
                }catch (e: JSONException){
                    "Unknown Error Occured"
                }
                emit(Result.Error(errorMessage))
            }
        }catch (e: Exception){
            emit(Result.Error(e.message ?: "An error occurred"))
        }
    }
}