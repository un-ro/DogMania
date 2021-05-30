package com.unero.dogmania.core.data.source.remote.network

import com.unero.dogmania.core.data.source.remote.response.RandomResponse
import retrofit2.http.GET

interface Endpoint {
    @GET("image/random/10")
    suspend fun getRandomFor(): RandomResponse
}