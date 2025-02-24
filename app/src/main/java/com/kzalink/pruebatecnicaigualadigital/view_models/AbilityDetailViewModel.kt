package com.kzalink.pruebatecnicaigualadigital.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kzalink.pruebatecnicaigualadigital.api.AbilityRepository
import com.kzalink.pruebatecnicaigualadigital.data.AbilityDetailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AbilityDetailViewModel @Inject constructor(
    private val repository: AbilityRepository
) : ViewModel() {

    private val _abilityDetail = MutableLiveData<AbilityDetailResponse>()
    val abilityDetail: LiveData<AbilityDetailResponse> = _abilityDetail

    private val _spanishName = MutableLiveData<String>()
    val spanishName: LiveData<String> = _spanishName

    private val _spanishEffect = MutableLiveData<String>()
    val spanishEffect: LiveData<String> = _spanishEffect

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadAbilityDetail(abilityUrl: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            // Extraer el ID de la URL
            val abilityId = repository.getIdFromUrl(abilityUrl)
            val result = repository.getAbilityDetail(abilityId)

            result.fold(
                onSuccess = { response ->
                    _abilityDetail.value = response

                    // Filtrar el nombre en español
                    val spanishName = response.names.find { it.language.name.equals("es",true) }?.name
                        ?: response.name
                    _spanishName.value = spanishName

                    // Filtrar el efecto en español
                    val spanishEffect = response.effect_entries.find { it.language.name.equals("es",true) }?.effect
                        ?: response.effect_entries.firstOrNull()?.effect
                        ?: "No hay descripción disponible"
                    _spanishEffect.value = spanishEffect
                },
                onFailure = { exception ->
                    _error.value = exception.message
                }
            )

            _isLoading.value = false
        }
    }
}