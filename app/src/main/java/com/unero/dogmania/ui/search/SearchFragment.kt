package com.unero.dogmania.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.unero.dogmania.R
import com.unero.dogmania.adapter.ItemAdapter
import com.unero.dogmania.core.data.source.remote.network.ApiResponse
import com.unero.dogmania.core.domain.model.Dog
import com.unero.dogmania.core.utils.Mapper
import com.unero.dogmania.databinding.FragmentSearchBinding
import org.koin.android.ext.android.inject

class SearchFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val viewModel by inject<SearchViewModel>()
    private val itemAdapter by inject<ItemAdapter>()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSpinner()
        binding.spBreed.onItemSelectedListener = this
        binding.toolbar.setNavigationOnClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        val selected = parent.getItemAtPosition(position)
        viewModel.search(selected.toString()).observe(viewLifecycleOwner, {
            when (it) {
                is ApiResponse.Empty -> Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show()
                is ApiResponse.Error -> Toast.makeText(requireContext(), "Cannot Search for $selected", Toast.LENGTH_SHORT).show()
                is ApiResponse.Success -> {
                    setupRV(Mapper.mapResponseToDomain(it.data))
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    private fun setupRV(list: List<Dog>) {
        with(binding.rvResult) {
            itemAdapter.setList(list)
            adapter = itemAdapter
            setHasFixedSize(true)
        }

        itemAdapter.onItemClick = {
            Toast.makeText(requireContext(), "Search only show list.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setSpinner(){
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.breeds,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            binding.spBreed.adapter = adapter
        }
    }
}