package com.unero.dogmania.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.unero.dogmania.search.databinding.ActivitySearchBinding
import org.koin.android.ext.android.inject

class SearchActivity : AppCompatActivity() {

    private val viewModel: SearchViewModel by inject()
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}