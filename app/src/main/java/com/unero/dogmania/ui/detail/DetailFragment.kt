package com.unero.dogmania.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.unero.dogmania.R
import com.unero.dogmania.databinding.FragmentDetailBinding
import org.koin.android.ext.android.inject

class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by inject()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()
    }

    private fun setUI() {
        val progress = CircularProgressDrawable(requireContext())
        progress.strokeWidth = 5f
        progress.centerRadius = 30f
        progress.start()
        with(binding) {
            Glide.with(requireContext())
                .load(args.item.image)
                .placeholder(progress)
                .error(R.drawable.ic_error_image)
                .into(ivImage)
        }
    }
}