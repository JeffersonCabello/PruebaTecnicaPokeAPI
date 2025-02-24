package com.kzalink.pruebatecnicaigualadigital

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AbilityAdapter(
    private val abilities: MutableList<Ability> = mutableListOf(),
    private val onItemClick: (Ability) -> Unit
) : RecyclerView.Adapter<AbilityAdapter.AbilityViewHolder>() {

    class AbilityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.textViewAbilityName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbilityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ability, parent, false)
        return AbilityViewHolder(view)
    }

    override fun onBindViewHolder(holder: AbilityViewHolder, position: Int) {
        val ability = abilities[position]
        holder.nameTextView.text = ability.name.capitalize()

        holder.itemView.setOnClickListener {
            onItemClick(ability)
        }
    }

    override fun getItemCount() = abilities.size

    fun updateData(newAbilities: List<Ability>) {
        val startPosition = abilities.size
        abilities.addAll(newAbilities)
        notifyItemRangeInserted(startPosition, newAbilities.size)
    }
}