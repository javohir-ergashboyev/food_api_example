package com.example.easyfood.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.easyfood.R
import com.example.easyfood.networkService.CheckNetworkConnectivity
import com.example.easyfood.remote.repository.CategoryRepository
import com.example.easyfood.remote.repository.PopularRepository
import com.example.easyfood.remote.repository.RandomMealRepository
import com.example.easyfood.ui.vm.CategoryVM
import com.example.easyfood.ui.vm.HomeVM
import com.example.easyfood.ui.vmFactory.CategoryVMFactory
import com.example.easyfood.ui.vmFactory.HomeVMFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: HomeVM
    lateinit var viewModelCategory: CategoryVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //home viewModel
        val mealRepository = RandomMealRepository()
        val categoryRepository = CategoryRepository()
        val popularRepository = PopularRepository()
        val vmFactory =
            HomeVMFactory(
                mealRepository = mealRepository,
                categoryRepository = categoryRepository,
                popularRepository = popularRepository
            )
        viewModel = ViewModelProvider(this, vmFactory)[HomeVM::class.java]

        //category viewModel
        val categoryMainRepository = CategoryRepository()
        val categoryVMFactory = CategoryVMFactory(categoryMainRepository)
        viewModelCategory = ViewModelProvider(this, categoryVMFactory)[CategoryVM::class.java]

        //Setup Navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.btm_nav)
        val navController = Navigation.findNavController(this, R.id.fragmentContainerView)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        //check network state
        val networkUnavailable = findViewById<TextView>(R.id.network_unavailable)
        val checkNetworkState = CheckNetworkConnectivity(this)
        if (checkNetworkState.value == null) {
            networkUnavailable.visibility = View.VISIBLE
        }
        checkNetworkState.observe(this) {
            if (it) {
                networkUnavailable.visibility = View.GONE
            } else {
                networkUnavailable.visibility = View.VISIBLE
            }
        }

    }
}


















