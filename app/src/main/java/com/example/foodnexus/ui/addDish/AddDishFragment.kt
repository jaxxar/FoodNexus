package com.example.foodnexus.ui.addDish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodnexus.databinding.FragmentAddDishBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddDishFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddDishBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddDishBinding.inflate(layoutInflater)
        return binding.root
    }

}