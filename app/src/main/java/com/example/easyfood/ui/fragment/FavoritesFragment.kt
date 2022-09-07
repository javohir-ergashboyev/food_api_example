package com.example.easyfood.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.easyfood.adapter.FavoritesAdapter
import com.example.easyfood.databinding.FragmentFavoritesBinding
import com.example.easyfood.db.FavoritesDatabase
import com.example.easyfood.remote.repository.FavoritesRepository
import com.example.easyfood.ui.vm.FavoritesVM
import com.example.easyfood.ui.vmFactory.FavoritesVMFactory

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FavoritesAdapter(requireContext())
        binding.rvFavorites.adapter = adapter

        val db = FavoritesDatabase(requireContext())

        val repository = FavoritesRepository(db)
        val vmFactory = FavoritesVMFactory(repository)
        val viewModel = ViewModelProvider(viewModelStore, vmFactory)[FavoritesVM::class.java]

        viewModel.getFavorites().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapter.differ.submitList(it)
                Log.d("FAVORITES", it.toString())
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}