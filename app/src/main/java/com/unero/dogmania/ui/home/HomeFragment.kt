package com.unero.dogmania.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.unero.dogmania.core.adapter.ItemAdapter
import com.unero.dogmania.core.data.Resource
import com.unero.dogmania.databinding.FragmentHomeBinding
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by inject()
    private val itemAdapter: ItemAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
        viewModel.dog.observe(viewLifecycleOwner, { dogs ->
            if (dogs != null) {
                when (dogs) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        showLoading(false)
                        dogs.data?.let { itemAdapter.setList(it) }
                    }
                    is Resource.Error -> {
                        showLoading(false)
                    }
                }
            }
        })
    }

    private fun setupRV() {
        with(binding.rvDog) {
            adapter = itemAdapter
            setHasFixedSize(true)
        }
    }

    private fun showLoading(boolean: Boolean) {
        if (boolean) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}