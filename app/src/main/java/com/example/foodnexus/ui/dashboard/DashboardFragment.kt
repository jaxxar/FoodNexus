package com.example.foodnexus.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodnexus.MainActivity
import com.example.foodnexus.databinding.FragmentDashboardBinding
import com.example.foodnexus.model.DishesData
import com.example.foodnexus.ui.adapters.DishAdapter
import com.example.foodnexus.ui.adapters.DishCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment(), DishCallback {

    private val dashboardViewModel: DashboardViewModel by viewModels()
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var dishAdapter: DishAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dishesRecyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
        dishAdapter = DishAdapter(this@DashboardFragment, this, false)
        binding.dishesRecyclerview.adapter = dishAdapter
        chipListeners()
    }

    private fun chipListeners() {
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            if (group.checkedChipId == binding.chipFavourites.id) {
                dashboardViewModel.favouriteDishes.observe(viewLifecycleOwner) {
                    if (it.isNotEmpty()) {
                        binding.dishesRecyclerview.visibility = View.VISIBLE
                        binding.noItemsAvailableTextview.visibility = View.GONE
                        dishAdapter.submitList(it)
                    } else {
                        binding.dishesRecyclerview.visibility = View.GONE
                        binding.noItemsAvailableTextview.visibility = View.VISIBLE
                    }
                }
            } else {

            }
            if (group.checkedChipId == binding.chipFast.id) {
                dashboardViewModel.quickDishes.observe(viewLifecycleOwner) {
                    if (it.isNotEmpty()) {
                        binding.dishesRecyclerview.visibility = View.VISIBLE
                        binding.noItemsAvailableTextview.visibility = View.GONE
                        dishAdapter.submitList(it)
                    } else {
                        binding.dishesRecyclerview.visibility = View.GONE
                        binding.noItemsAvailableTextview.visibility = View.VISIBLE
                    }
                }
            } else {

            }
            if (group.checkedChipId == binding.chipEasy.id) {
                dashboardViewModel.easyDishes.observe(viewLifecycleOwner) {
                    if (it.isNotEmpty()) {
                        binding.dishesRecyclerview.visibility = View.VISIBLE
                        binding.noItemsAvailableTextview.visibility = View.GONE
                        dishAdapter.submitList(it)
                    } else {
                        binding.dishesRecyclerview.visibility = View.GONE
                        binding.noItemsAvailableTextview.visibility = View.VISIBLE
                    }
                }
            } else {

            }
            if (group.checkedChipId == binding.chipMedium.id) {
                dashboardViewModel.mediumDishes.observe(viewLifecycleOwner) {
                    if (it.isNotEmpty()) {
                        binding.dishesRecyclerview.visibility = View.VISIBLE
                        binding.noItemsAvailableTextview.visibility = View.GONE
                        dishAdapter.submitList(it)
                    } else {
                        binding.dishesRecyclerview.visibility = View.GONE
                        binding.noItemsAvailableTextview.visibility = View.VISIBLE
                    }
                }
            } else {

            }
            if (group.checkedChipId == binding.chipHard.id) {
                dashboardViewModel.hardDishes.observe(viewLifecycleOwner) {
                    if (it.isNotEmpty()) {
                        binding.dishesRecyclerview.visibility = View.VISIBLE
                        binding.noItemsAvailableTextview.visibility = View.GONE
                        dishAdapter.submitList(it)
                    } else {
                        binding.dishesRecyclerview.visibility = View.GONE
                        binding.noItemsAvailableTextview.visibility = View.VISIBLE
                    }
                }
            } else {

            }
        }
    }

    override fun returnEditDish(dish: DishesData) {
        TODO("Not yet implemented")
    }

    override fun returnDetailsDish(dish: DishesData) {
        val action =
            DashboardFragmentDirections.actionNavigationDashboardToDishDetailsFragment(dish)
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