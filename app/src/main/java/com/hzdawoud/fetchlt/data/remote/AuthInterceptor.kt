package com.hzdawoud.fetchlt.data.remote

import com.hzdawoud.fetchlt.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Add the access token as a query parameter
        val newUrl = originalRequest.url.newBuilder()
            .addQueryParameter("access_key", BuildConfig.API_ACCESS_TOKEN)
            .build()

        // Build the new request with the modified URL
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}