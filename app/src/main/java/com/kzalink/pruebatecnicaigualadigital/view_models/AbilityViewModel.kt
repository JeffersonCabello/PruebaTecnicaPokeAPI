package com.kzalink.pruebatecnicaigualadigital.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kzalink.pruebatecnicaigualadigital.api.AbilityRepository
import com.kzalink.pruebatecnicaigualadigital.data.Ability
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AbilityViewModel @Inject constructor(
    private val repository: AbilityRepository
) : ViewModel() {

    private val _abilities = MutableLiveData<List<Ability>>()
    val abilities: LiveData<List<Ability>> = _abilities

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private var offset = 0
    private val limit = 10

    init {
        loadAbilities()
    }

    fun loadAbilities() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = repository.getAbilities(limit, offset)

            result.fold(
                onSuccess = { response ->
                    val currentList = _abilities.value ?: emptyList()
                    _abilities.value = currentList + response.results
                    offset += limit
                },
                onFailure = { exception ->
                    _error.value = exception.message
                }
            )

            _isLoading.value = false
        }
    }
}