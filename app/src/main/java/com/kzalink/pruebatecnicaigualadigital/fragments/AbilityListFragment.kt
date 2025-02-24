package com.kzalink.pruebatecnicaigualadigital.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kzalink.pruebatecnicaigualadigital.view_models.AbilityViewModel
import com.kzalink.pruebatecnicaigualadigital.R
import com.kzalink.pruebatecnicaigualadigital.adapters.AbilityAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AbilityListFragment : Fragment() {

    private val viewModel: AbilityViewModel by viewModels()
    private lateinit var adapter: AbilityAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ability_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewAbilities)
        adapter = AbilityAdapter { ability ->
            // Navegar al detalle con los datos de la habilidad
            val bundle = Bundle().apply {
                putString("abilityName", ability.name)
                putString("abilityUrl", ability.url)
            }
            findNavController().navigate(R.id.action_abilityListFragment_to_abilityDetailFragment, bundle)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Agregar ScrollListener para cargar más elementos
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && dy > 0) {
                    viewModel.loadAbilities()
                }
            }
        })

        // Observar los cambios en los datos
        viewModel.abilities.observe(viewLifecycleOwner) { abilities ->
            adapter.updateData(abilities)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            view.findViewById<View>(R.id.progressBar).visibility =
                if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
    }
}