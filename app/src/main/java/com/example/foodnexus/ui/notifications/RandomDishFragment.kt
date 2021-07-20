package com.example.foodnexus.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.foodnexus.databinding.FragmentRandomDishBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RandomDishFragment : Fragment() {

    private lateinit var randomDishViewModel: RandomDishViewModel
    private lateinit var binding: FragmentRandomDishBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRandomDishBinding.inflate(inflater)

        return binding.root
    }
}