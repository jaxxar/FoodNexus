package com.example.foodnexus.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodnexus.R
import com.example.foodnexus.databinding.FragmentAllDishesBinding
import com.example.foodnexus.ui.adapters.DishAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentAllDishesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllDishesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dishesRecyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
        val dishAdapter = DishAdapter(this@HomeFragment)
        binding.dishesRecyclerview.adapter = dishAdapter
        homeViewModel.allDishes.observe(viewLifecycleOwner) {
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_dish_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_add_dish -> {
                val action = HomeFragmentDirections.actionNavigationHomeToAddDishFragment()
                findNavController().navigate(action)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}