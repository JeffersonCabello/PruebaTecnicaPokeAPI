package com.kzalink.pruebatecnicaigualadigital

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AbilityDetailFragment : Fragment() {

    private lateinit var viewModel: AbilityDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ability_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar ViewModel
        viewModel = ViewModelProvider(this).get(AbilityDetailViewModel::class.java)

        // Obtener la URL de la habilidad de los argumentos
        val abilityUrl = arguments?.getString("abilityUrl") ?: ""

        // Configurar observadores
        viewModel.spanishName.observe(viewLifecycleOwner) { name ->
            view.findViewById<TextView>(R.id.textViewAbilityDetailName).text = name
        }

        viewModel.spanishEffect.observe(viewLifecycleOwner) { effect ->
            view.findViewById<TextView>(R.id.textViewAbilityDetailEffect).text = effect
        }

        viewModel.abilityDetail.observe(viewLifecycleOwner) { ability ->
            // Configurar el RecyclerView para mostrar los pokémon
            val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewPokemon)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = PokemonAdapter(ability.pokemon)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            view.findViewById<View>(R.id.progressBarDetail).visibility =
                if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }

        // Cargar los detalles de la habilidad
        viewModel.loadAbilityDetail(abilityUrl)
    }
}