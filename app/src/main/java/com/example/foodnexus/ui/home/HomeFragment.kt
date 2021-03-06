package com.example.foodnexus.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodnexus.MainActivity
import com.example.foodnexus.R
import com.example.foodnexus.databinding.FragmentAllDishesBinding
import com.example.foodnexus.model.DishesData
import com.example.foodnexus.ui.adapters.DishAdapter
import com.example.foodnexus.ui.adapters.DishCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), DishCallback {

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
        val dishAdapter = DishAdapter(this@HomeFragment, this, true)
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

    override fun returnEditDish(dish: DishesData) {
        val action = HomeFragmentDirections.actionNavigationHomeToAddDishFragment(dish)
        findNavController().navigate(action)
    }

    override fun returnDeleteDish(dish: DishesData) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(resources.getString(R.string.title_delete_dish))
        builder.setMessage(resources.getString(R.string.msg_delete_dish, dish.title))
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton(resources.getString(R.string.ans_yes)) { dialogInterface, _ ->
            homeViewModel.deleteDish(dish)
            dialogInterface.dismiss()
        }
        builder.setNegativeButton(resources.getString(R.string.ans_no)) { dialogInterface, _ ->
            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun returnDetailsDish(dish: DishesData) {
        val action = HomeFragmentDirections.actionNavigationAllDishesToDishDetailsFragment(dish)
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