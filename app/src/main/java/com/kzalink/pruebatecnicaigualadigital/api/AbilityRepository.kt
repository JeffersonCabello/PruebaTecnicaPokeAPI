package com.kzalink.pruebatecnicaigualadigital.api

import com.kzalink.pruebatecnicaigualadigital.data.AbilityDetailResponse
import com.kzalink.pruebatecnicaigualadigital.data.AbilityResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AbilityRepository @Inject constructor(private val apiService: ApiService) {

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