package com.unero.dogmania.loved

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.unero.dogmania.adapter.ItemAdapter
import com.unero.dogmania.loved.databinding.ActivityLovedBinding
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

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

        itemAdapter.onItemClick = {
            Toast.makeText(this, "Favorite only show list.", Toast.LENGTH_SHORT).show()
        }

        viewModel.favorites.observe(this, { dogs ->
            itemAdapter.setList(dogs)
            itemAdapter.notifyDataSetChanged()
            binding.lottieEmpty.visibility = if (dogs.isNotEmpty()) View.GONE else View.VISIBLE
            binding.tvStatus.visibility = if (dogs.isNotEmpty()) View.GONE else View.VISIBLE
        })
    }

    // Fix Memory Leak
    override fun onStop() {
        binding.rvDog.adapter = null
        unloadKoinModules(lovedModule)
        super.onStop()
    }
}