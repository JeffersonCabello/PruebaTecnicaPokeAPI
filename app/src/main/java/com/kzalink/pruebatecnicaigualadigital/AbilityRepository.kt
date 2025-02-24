package com.kzalink.pruebatecnicaigualadigital

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AbilityRepository {
    private val apiService: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    suspend fun getAbilities(limit: Int, offset: Int): Result<AbilityResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getAbilities(limit, offset)
                if (response.isSuccessful) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Error: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getAbilityDetail(id: Int): Result<AbilityDetailResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getAbilityDetail(id)
                if (response.isSuccessful) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Error: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // Función para extraer el ID de la URL de la habilidad
    fun getIdFromUrl(url: String): Int {
        val regex = "https://pokeapi.co/api/v2/ability/(\\d+)/".toRegex()
        val matchResult = regex.find(url)
        return matchResult?.groupValues?.get(1)?.toInt() ?: 1
    }
}