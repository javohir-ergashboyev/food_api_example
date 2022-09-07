package com.example.easyfood.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.databinding.ActivityDetailBinding
import com.example.easyfood.db.FavoritesDatabase
import com.example.easyfood.db.FavoritesModel
import com.example.easyfood.networkService.CheckNetworkConnectivity
import com.example.easyfood.remote.repository.DetailRepository
import com.example.easyfood.remote.repository.FavoritesRepository
import com.example.easyfood.resource.Resource
import com.example.easyfood.ui.vm.DetailVM
import com.example.easyfood.ui.vm.FavoritesVM
import com.example.easyfood.ui.vmFactory.DetailVMFactory
import com.example.easyfood.ui.vmFactory.FavoritesVMFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var mealImg: String
    private lateinit var mealTitle: String
    private lateinit var mealId: String
    private lateinit var meal: FavoritesModel
    private lateinit var youtubeUri: String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        getMealDetails()

        Glide.with(this).load(mealImg).into(binding.mealImage)
        binding.collapsingToolBar.title = mealTitle
        binding.collapsingToolBar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolBar.setExpandedTitleColor(resources.getColor(R.color.white))

        //setup vm
        val repository = DetailRepository()
        val vmFactory = DetailVMFactory(repository)
        val viewModel = ViewModelProvider(this, vmFactory)[DetailVM::class.java]

        val checkNetwork = CheckNetworkConnectivity(this)
        checkNetwork.observe(this) {
            if (it) {
                viewModel.getMealDetails(mealId)
            }
        }
        viewModel.currentMeal.observe(this) {
            when (it) {
                is Resource.Success -> {
                    removePB(binding)
                    it.data?.let { response ->
                        binding.tvDescription.text = response.meals[0].strInstructions
                        youtubeUri = response.meals[0].strYoutube
                        binding.mealCategory.text = response.meals[0].strCategory
                        binding.mealArea.text = response.meals[0].strArea
                        meal = FavoritesModel(
                            mealName = response.meals[0].strMeal,
                            mealCategory = response.meals[0].strCategory,
                            mealImage = response.meals[0].strMealThumb,
                            mealId = response.meals[0].idMeal,
                            mealDescription = response.meals[0].strInstructions
                        )

                    }
                }
                is Resource.Error -> {
                    removePB(binding)
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    showPB(binding)
                }
            }
        }

        binding.youtube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUri))
            startActivity(intent)
        }
        //save room
        val db = FavoritesDatabase(this)
        val repo = FavoritesRepository(db)
        val vmf = FavoritesVMFactory(repo)
        val vm = ViewModelProvider(this, vmf)[FavoritesVM::class.java]

        binding.fabFavorite.setOnClickListener {
            try {
                vm.saveMeal(meal)
                showToast("Successfully added")
            } catch (e: Exception) {
                showToast("Error added")
            }
        }


    }

    private fun getMealDetails() {
        val intent = intent
        mealImg = intent.getStringExtra("meal_img").toString()
        mealTitle = intent.getStringExtra("meal_title")!!
        mealId = intent.getStringExtra("meal_id")!!
    }

    private fun showPB(binding: ActivityDetailBinding) {
        binding.pbMealDetails.visibility = View.VISIBLE
    }

    private fun removePB(binding: ActivityDetailBinding) {
        binding.pbMealDetails.visibility = View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}