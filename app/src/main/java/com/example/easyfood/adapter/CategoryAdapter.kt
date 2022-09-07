package com.example.easyfood.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.categoryModel.Category
import com.example.easyfood.databinding.CategoryItemBinding
import com.example.easyfood.ui.activity.MealsActivity

class CategoryAdapter(private val context: Context) :
    RecyclerView.Adapter<CategoryAdapter.Holder>() {

    inner class Holder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            Glide.with(context).load(differ.currentList[pos].strCategoryThumb)
                .into(binding.categoryImage)
            binding.categoryName.text = differ.currentList[pos].strCategory
            binding.root.setOnClickListener {
                val intent = Intent(context, MealsActivity::class.java)
                intent.putExtra("cat_name", differ.currentList[pos].strCategory)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            CategoryItemBinding.inflate(
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

    private val diffUtil = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

}