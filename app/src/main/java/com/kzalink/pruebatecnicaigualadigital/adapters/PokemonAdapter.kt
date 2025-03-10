package com.kzalink.pruebatecnicaigualadigital.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kzalink.pruebatecnicaigualadigital.R
import com.kzalink.pruebatecnicaigualadigital.data.AbilityPokemon
import java.util.Locale

class PokemonAdapter(
    private val pokemonList: List<AbilityPokemon>
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.textViewPokemonName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        val pokemonName = pokemon.pokemon.name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }

        // Mostrar si la habilidad es oculta
        val displayText = if (pokemon.is_hidden) {
            "$pokemonName (Habilidad oculta)"
        } else {
            pokemonName
        }

        holder.nameTextView.text = displayText
    }

    override fun getItemCount() = pokemonList.size
}