package com.example.easyfood.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.databinding.PopularMealItemBinding
import com.example.easyfood.randomModel.Meal
import com.example.easyfood.ui.activity.DetailActivity

class PopularAdapter(private val context: Context) : RecyclerView.Adapter<PopularAdapter.Holder>() {

    inner class Holder(private val binding: PopularMealItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            Glide.with(context).load(differ.currentList[pos].strMealThumb)
                .into(binding.popularImage)
            binding.popularImage.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("meal_img", differ.currentList[pos].strMealThumb)
                intent.putExtra("meal_title", differ.currentList[pos].strMeal)
                intent.putExtra("meal_id", differ.currentList[pos].idMeal)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            PopularMealItemBinding.inflate(
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

    private val diffUtil = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)
}