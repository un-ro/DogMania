package com.unero.dogmania.favorite.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.unero.dogmania.favorite.databinding.ActivityFavoriteBinding
import org.koin.android.ext.android.inject

class FavoriteActivity : AppCompatActivity() {

    private val viewModel: FavoriteViewModel by inject()
    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}