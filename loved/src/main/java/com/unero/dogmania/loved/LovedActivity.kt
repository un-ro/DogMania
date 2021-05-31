package com.unero.dogmania.loved

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.unero.dogmania.adapter.ItemAdapter
import com.unero.dogmania.loved.databinding.ActivityLovedBinding
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules

class LovedActivity : AppCompatActivity() {

    private val viewModel by inject<LovedViewModel>()
    private val itemAdapter by inject<ItemAdapter>()
    private lateinit var binding: ActivityLovedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLovedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(lovedModule)

        with (binding.rvDog) {
            adapter = itemAdapter
            setHasFixedSize(true)
        }

        viewModel.favorites.observe(this, { dogs ->
            itemAdapter.setList(dogs)
            itemAdapter.notifyDataSetChanged()
            binding.tvStatus.visibility = if (dogs.isNotEmpty()) View.GONE else View.VISIBLE
        })
    }
}