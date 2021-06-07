package com.unero.dogmania.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.widget.RxTextView
import com.unero.dogmania.R
import com.unero.dogmania.databinding.FragmentDetailBinding
import org.koin.android.ext.android.inject

class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by inject()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()

        val commentStream = RxTextView.textChanges(binding.edtTitle)
            .map { comment ->
                (comment.length > 15) || (comment.isEmpty())
            }
        commentStream.subscribe {
            setButton(it)
        }

        binding.btnFavorite.setOnClickListener {
            viewModel.setComment(args.item, binding.edtTitle.text.toString())
            binding.btnFavorite.text = getString(R.string.update_done)
        }

        binding.toolbar.setNavigationOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }

    private fun setButton(notValid: Boolean) {
        binding.edtTitle.error = if (notValid) getString(R.string.update_error) else null
        binding.btnFavorite.isEnabled = !notValid
    }

    private fun setUI() {
        var status = args.item.isFavorite
        setStatusFavorite(status)

        with(binding) {
            tvUrl.text = args.item.image
            edtTitle.setText(args.item.comment)
            Glide.with(requireContext())
                .load(args.item.image)
                .placeholder(loadIndicator())
                .error(R.drawable.ic_error_image)
                .into(ivImage)

            // Favorite
            fab.setOnClickListener {
                status = !status
                viewModel.setFavorite(args.item, status)
                setStatusFavorite(status)
            }
            if (args.item.date.isNotEmpty()) {
                binding.btnFavorite.text = getString(R.string.update_last, args.item.date)
            }
        }
    }

    private fun loadIndicator(): CircularProgressDrawable{
        val progress = CircularProgressDrawable(requireContext())
        progress.strokeWidth = 5f
        progress.centerRadius = 30f
        progress.start()
        return progress
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_fill))
            binding.edtTitle.isEnabled = true
            binding.btnFavorite.isEnabled = true
        } else {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite))
            binding.btnFavorite.text = getString(R.string.fav_information)
            binding.edtTitle.isEnabled = false
            binding.btnFavorite.isEnabled = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}