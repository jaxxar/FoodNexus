package com.example.foodnexus.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.foodnexus.databinding.FragmentRandomDishBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RandomDishFragment : Fragment() {

    private val randomDishViewModel: RandomDishViewModel by viewModels()
    private var binding: FragmentRandomDishBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRandomDishBinding.inflate(inflater)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        randomDishViewModel.getRandomDishFromAPI()

        randomDishViewModelObserver()

    }

    private fun randomDishViewModelObserver() {
        randomDishViewModel.randomDishResponse.observe(viewLifecycleOwner,
            { randomDishResponse ->
                randomDishResponse?.let {
                    Log.d("Random Dish loaded successfully", "${randomDishResponse.title}")
                }
            })

        randomDishViewModel.randomDishLoadingError.observe(viewLifecycleOwner,
            { error ->
                error?.let {
                    Log.d("Random Dish API Error", "$error")
                }
            })

        randomDishViewModel.loadRandomDish.observe(viewLifecycleOwner,
            { loading ->
                loading?.let {
                    Log.d("Random Dish loading: ", "$loading")
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}