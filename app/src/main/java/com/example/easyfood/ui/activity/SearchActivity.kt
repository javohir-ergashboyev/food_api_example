package com.example.easyfood.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easyfood.adapter.SearchAdapter
import com.example.easyfood.databinding.ActivitySearchBinding
import com.example.easyfood.networkService.CheckNetworkConnectivity
import com.example.easyfood.remote.repository.SearchRepository
import com.example.easyfood.resource.Resource
import com.example.easyfood.ui.vm.SearchVM
import com.example.easyfood.ui.vmFactory.SearchVMFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var adapter: SearchAdapter
    private lateinit var viewModel: SearchVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        //viewModel setup
        val repository = SearchRepository()
        val vmFactory = SearchVMFactory(repository)
        viewModel = ViewModelProvider(this, vmFactory)[SearchVM::class.java]

        adapter = SearchAdapter(this)
        binding.rvSearch.adapter = adapter
        binding.rvSearch.layoutManager = LinearLayoutManager(this)

        var job: Job? = null
        binding.edtSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                if (editable.toString().isNotEmpty()) {
                    editable?.let {
                        val checkNetwork = CheckNetworkConnectivity(this@SearchActivity)
                        checkNetwork.observe(this@SearchActivity) {
                            if (it) {
                                viewModel.searchMeal(editable.toString())
                            }
                        }

                    }
                }
            }
        }

        viewModel.searchLiveData.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    removePB(binding)
                    resource.data?.let {
                        adapter.differ.submitList(it.meals)
                    }
                }
                is Resource.Error -> {
                    removePB(binding)

                }
                is Resource.Loading -> {
                    showPB(binding)
                }
            }
        }

    }

    private fun removePB(binding: ActivitySearchBinding) {
        binding.pbSearch.visibility = View.GONE
        binding.rvSearch.visibility = View.VISIBLE
    }

    private fun showPB(binding: ActivitySearchBinding) {
        binding.pbSearch.visibility = View.VISIBLE
        binding.rvSearch.visibility = View.GONE
    }
}