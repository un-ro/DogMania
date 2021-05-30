package com.unero.dogmania.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.unero.dogmania.core.databinding.ItemBinding

class HomeAdapter(private val context: Context): RecyclerView.Adapter<HomeAdapter.HomveViewHolder>() {

    private val list = mutableListOf<String>()

    fun setData(newList: List<String>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    class HomveViewHolder(private val binding: ItemBinding, context: Context): RecyclerView.ViewHolder(binding.root) {

        private val progress = CircularProgressDrawable(context)

        fun bind(image: String) {
            progress.strokeWidth = 5f
            progress.centerRadius = 30f
            progress.start()
            binding.apply {
                Glide.with(root)
                    .load(image)
                    .placeholder(progress)
                    .into(ivImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomveViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomveViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: HomveViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}