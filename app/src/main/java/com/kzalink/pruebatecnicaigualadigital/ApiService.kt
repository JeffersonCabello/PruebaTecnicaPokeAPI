package com.kzalink.pruebatecnicaigualadigital

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("ability/")
    suspend fun getAbilities(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<AbilityResponse>

    @GET("ability/{id}/")
    suspend fun getAbilityDetail(@Path("id") id: Int): Response<AbilityDetailResponse>
}