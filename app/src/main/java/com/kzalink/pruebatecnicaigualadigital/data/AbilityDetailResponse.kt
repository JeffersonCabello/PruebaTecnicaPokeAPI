package com.kzalink.pruebatecnicaigualadigital.data

data class AbilityDetailResponse(
    val id: Int,
    val name: String,
    val names: List<AbilityName>,
    val effect_entries: List<AbilityEffect>,
    val pokemon: List<AbilityPokemon>
)