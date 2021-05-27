package com.unero.dogmania.core.data.source.remote.network

import com.unero.dogmania.core.data.source.remote.response.RandomResponse
import retrofit2.http.GET

interface Endpoint {
    @GET("image/random")
    suspend fun getRandom(): RandomResponse

    @GET("image/random/")
    suspend fun getRandomFor(): RandomResponse
}