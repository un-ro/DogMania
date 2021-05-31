package com.unero.dogmania.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.unero.dogmania.favorite.databinding.ActivityFavoriteBinding
import org.koin.android.ext.android.inject

class FavoriteActivity : AppCompatActivity() {

    private val viewModel: FavoriteViewModel by inject()
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}