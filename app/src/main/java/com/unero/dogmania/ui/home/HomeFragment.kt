package com.unero.dogmania.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.unero.dogmania.R
import com.unero.dogmania.adapter.ItemAdapter
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

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_favorite -> {
                    val uri = Uri.parse("dogmania://loved")
                    startActivity(Intent(Intent.ACTION_VIEW, uri))
                    true
                }
                R.id.item_search -> {
                    val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
                    findNavController().navigate(action)
                    true
                }
                else -> false
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            Toast.makeText(requireContext(), "Woof woof", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        setupRV()
    }

    private fun setupRV() {
        with(binding.rvDog) {
            adapter = itemAdapter
            setHasFixedSize(true)
        }

        viewModel.dog.observe(viewLifecycleOwner, { dogs ->
            if (dogs != null) {
                when (dogs) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        showLoading(false)
                        dogs.data?.let { itemAdapter.setList(it) }
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show()
                        showLoading(false)
                    }
                }
            }
        })

        itemAdapter.onItemClick = { selected ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(selected)
            findNavController().navigate(action)
        }
    }

    private fun showLoading(boolean: Boolean) {
        if (boolean) {
            binding.lottieLoading.visibility = View.VISIBLE
        } else {
            binding.lottieLoading.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        binding.rvDog.adapter = null
        _binding = null
        super.onDestroyView()
    }
}