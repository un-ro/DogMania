package com.unero.dogmania.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.unero.dogmania.core.data.Resource.*
import com.unero.dogmania.databinding.FragmentHomeBinding
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.dog.observe(viewLifecycleOwner, { dog ->
            when (dog) {
                is Loading -> binding.progressBar.visibility = View.VISIBLE
                is Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvImageUrl.text = dog.data?.first()?.image
                    Glide.with(requireContext())
                        .load(dog.data?.first()?.image)
                        .into(binding.ivImage)
                }
                is Error -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}