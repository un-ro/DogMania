package com.unero.dogmania.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.unero.dogmania.core.data.source.remote.network.ApiResponse
import com.unero.dogmania.databinding.FragmentHomeBinding
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by inject()
    private lateinit var homeAdapter: HomeAdapter

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
        viewModel.dog.observe(viewLifecycleOwner, { dog ->
            when (dog) {
                is ApiResponse.Success -> {
                    homeAdapter.setData(dog.data.images)
                    homeAdapter.notifyDataSetChanged()
                    closeLoading()
                }
                is ApiResponse.Empty -> {
                    Toast.makeText(requireContext(), "Empty Data", Toast.LENGTH_SHORT).show()
                    closeLoading()
                }
                is ApiResponse.Error -> {
                    Toast.makeText(requireContext(), dog.errorMessage, Toast.LENGTH_SHORT).show()
                    closeLoading()
                }
            }
        })
    }

    private fun setupRV() {
        with(binding.rvDog) {
            homeAdapter = HomeAdapter(requireContext())
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun closeLoading() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}