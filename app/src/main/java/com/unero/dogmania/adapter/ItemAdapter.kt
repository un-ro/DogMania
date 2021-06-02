package com.unero.dogmania.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.unero.dogmania.core.R
import com.unero.dogmania.core.databinding.ItemBinding
import com.unero.dogmania.core.domain.model.Dog

class ItemAdapter(private val context: Context): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var list = mutableListOf<Dog>()
    var onItemClick: ((Dog) -> Unit)? = null

    fun setList(newList: List<Dog>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(private val binding: ItemBinding, context: Context):
        RecyclerView.ViewHolder(binding.root) {
        private val progress = CircularProgressDrawable(context)

        fun bind(dog: Dog) {
            progress.strokeWidth = 5f
            progress.centerRadius = 30f
            progress.start()
            binding.apply {

                binding.comment.text = if (dog.comment.isNotEmpty()) dog.comment else "Image from dog.ceo"

                Glide.with(root)
                    .load(dog.image)
                    .placeholder(progress)
                    .error(R.drawable.ic_error_image)
                    .into(ivImage)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(list[bindingAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}
