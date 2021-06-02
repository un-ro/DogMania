package com.unero.dogmania.core.data.source.remote.network

import com.unero.dogmania.core.data.source.remote.response.RandomResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface Endpoint {
    @GET("breeds/image/random/20")
    suspend fun getRandomFor(): RandomResponse

    @GET("breed/{breed}/images/random/10")
    suspend fun getSearch(@Path("breed") breed: String): RandomResponse
}
