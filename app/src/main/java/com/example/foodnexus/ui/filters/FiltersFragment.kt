package com.example.foodnexus.ui.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodnexus.MainActivity
import com.example.foodnexus.databinding.FragmentFiltersBinding
import com.example.foodnexus.model.DishesData
import com.example.foodnexus.ui.adapters.DishAdapter
import com.example.foodnexus.ui.adapters.DishCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FiltersFragment : Fragment(), DishCallback {

    private val filtersViewModel: FiltersViewModel by viewModels()
    private lateinit var binding: FragmentFiltersBinding
    private lateinit var dishAdapter: DishAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFiltersBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dishesRecyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
        dishAdapter = DishAdapter(this@FiltersFragment, this, false)
        binding.dishesRecyclerview.adapter = dishAdapter
        chipListeners()
    }

    private fun chipListeners() {
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.chipFavourites.id -> {
                    filtersViewModel.favouriteDishes.observe(viewLifecycleOwner) {
                        if (it.isNotEmpty()) {
                            binding.dishesRecyclerview.visibility = View.VISIBLE
                            binding.noItemsAvailableTextview.visibility = View.GONE
                            dishAdapter.submitList(it)
                        } else {
                            binding.dishesRecyclerview.visibility = View.GONE
                            binding.noItemsAvailableTextview.visibility = View.VISIBLE
                        }
                    }
                }
                binding.chipFast.id -> {
                    filtersViewModel.quickDishes.observe(viewLifecycleOwner) {
                        if (it.isNotEmpty()) {
                            binding.dishesRecyclerview.visibility = View.VISIBLE
                            binding.noItemsAvailableTextview.visibility = View.GONE
                            dishAdapter.submitList(it)
                        } else {
                            binding.dishesRecyclerview.visibility = View.GONE
                            binding.noItemsAvailableTextview.visibility = View.VISIBLE
                        }
                    }
                }
                binding.chipEasy.id -> {
                    filtersViewModel.easyDishes.observe(viewLifecycleOwner) {
                        if (it.isNotEmpty()) {
                            binding.dishesRecyclerview.visibility = View.VISIBLE
                            binding.noItemsAvailableTextview.visibility = View.GONE
                            dishAdapter.submitList(it)
                        } else {
                            binding.dishesRecyclerview.visibility = View.GONE
                            binding.noItemsAvailableTextview.visibility = View.VISIBLE
                        }
                    }
                }
                binding.chipMedium.id -> {
                    filtersViewModel.mediumDishes.observe(viewLifecycleOwner) {
                        if (it.isNotEmpty()) {
                            binding.dishesRecyclerview.visibility = View.VISIBLE
                            binding.noItemsAvailableTextview.visibility = View.GONE
                            dishAdapter.submitList(it)
                        } else {
                            binding.dishesRecyclerview.visibility = View.GONE
                            binding.noItemsAvailableTextview.visibility = View.VISIBLE
                        }
                    }
                }
                binding.chipHard.id -> {
                    filtersViewModel.hardDishes.observe(viewLifecycleOwner) {
                        if (it.isNotEmpty()) {
                            binding.dishesRecyclerview.visibility = View.VISIBLE
                            binding.noItemsAvailableTextview.visibility = View.GONE
                            dishAdapter.submitList(it)
                        } else {
                            binding.dishesRecyclerview.visibility = View.GONE
                            binding.noItemsAvailableTextview.visibility = View.VISIBLE
                        }
                    }
                }
                else -> {
                    binding.dishesRecyclerview.visibility = View.GONE
                    binding.noItemsAvailableTextview.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun returnEditDish(dish: DishesData) {
        //Not applicable
    }

    override fun returnDeleteDish(id: Int) {
        //Not applicable
    }

    override fun returnDetailsDish(dish: DishesData) {
        val action =
            FiltersFragmentDirections.actionNavigationFiltersToDishDetailsFragment(dish)
        findNavController().navigate(action)

        if (requireActivity() is MainActivity) {
            (activity as MainActivity).hideBottomNav()
        }
    }

    override fun onResume() {
        super.onResume()
        if (requireActivity() is MainActivity) {
            (activity as MainActivity).showBottomNav()
        }
    }
}