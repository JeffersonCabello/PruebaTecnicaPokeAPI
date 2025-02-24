package com.kzalink.pruebatecnicaigualadigital

data class AbilityResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Ability>
)
