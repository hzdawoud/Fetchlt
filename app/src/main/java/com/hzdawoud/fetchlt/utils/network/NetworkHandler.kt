package com.hzdawoud.fetchlt.utils.network

import android.util.Log
import retrofit2.Response

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: Throwable, val message: String? = null) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}

object NetworkHandler {
    // For production, consider a user-friendly error message; this is for testing purposes only.
    fun <T, R> Response<T>.toResource(tag: String, transform: (T) -> R): Resource<R> {
        return try {
            if (isSuccessful) {
                Log.d(tag, "getEndOfDayData - success")
                val body = body()
                if (body != null) {
                    Resource.Success(transform(body))
                } else {
                    Resource.Error(Exception("Response body is null"))
                }
            } else {
                Log.d(tag, "getEndOfDayData - api error")
                val errorMsg = errorBody()?.string()
                Resource.Error(Exception("API error: ${code()}"), errorMsg)
            }
        } catch (e: Exception) {
            Log.d(tag, "getEndOfDayData - error")
            Resource.Error(e, e.localizedMessage)
        }
    }
}