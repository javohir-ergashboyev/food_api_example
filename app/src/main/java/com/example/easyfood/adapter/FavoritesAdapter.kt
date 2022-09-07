package com.example.easyfood.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.databinding.SearchItemBinding
import com.example.easyfood.db.FavoritesModel
import com.example.easyfood.ui.activity.DetailActivity

class FavoritesAdapter(private val context: Context) :
    RecyclerView.Adapter<FavoritesAdapter.Holder>() {

    inner class Holder(private val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            Glide.with(context).load(differ.currentList[pos].mealImage)
                .into(binding.mealImg)
            binding.mealDescription.text = differ.currentList[pos].mealDescription
            binding.mealCategory.text = differ.currentList[pos].mealCategory
            binding.mealTitle.text = differ.currentList[pos].mealName

            binding.root.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("meal_img", differ.currentList[pos].mealImage)
                intent.putExtra("meal_title", differ.currentList[pos].mealName)
                intent.putExtra("meal_id", differ.currentList[pos].mealId)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            SearchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = differ.currentList.size

    private val diffUtil = object : DiffUtil.ItemCallback<FavoritesModel>() {
        override fun areItemsTheSame(oldItem: FavoritesModel, newItem: FavoritesModel): Boolean {
            return oldItem.mealId == newItem.mealId
        }

        override fun areContentsTheSame(oldItem: FavoritesModel, newItem: FavoritesModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)
}