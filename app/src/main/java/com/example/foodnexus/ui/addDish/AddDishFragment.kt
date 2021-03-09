package com.example.foodnexus.ui.addDish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.foodnexus.R
import com.example.foodnexus.databinding.FragmentAddDishBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddDishFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddDishBinding
    private lateinit var viewModel: AddDishViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddDishBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(AddDishViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addImage.setOnClickListener { }
        binding.addDishButton.setOnClickListener {
            if (validator()) {
                dismiss()
            }
        }
    }

    private fun validator(): Boolean {
        if (viewModel.isDataNotFilled(binding.textTitleEditText.text.toString())) {
            binding.textTitle.error = getString(R.string.please_fill_this_field)
            return false
        } else {
            binding.textTitle.error = null
        }
        if (viewModel.isDataNotFilled(binding.textTypeEditText.text.toString())) {
            binding.textType.error = getString(R.string.please_fill_this_field)
            return false
        } else {
            binding.textType.error = null
        }
        if (viewModel.isDataNotFilled(binding.textCategoryEditText.text.toString())) {
            binding.textCategory.error = getString(R.string.please_fill_this_field)
            return false
        } else {
            binding.textCategory.error = null
        }
        if (viewModel.isDataNotFilled(binding.textIngredientsEditText.text.toString())) {
            binding.textIngredients.error = getString(R.string.please_fill_this_field)
            return false
        } else {
            binding.textIngredients.error = null
        }
        if (viewModel.isDataNotFilled(binding.textTimeEditText.text.toString())) {
            binding.textTime.error = getString(R.string.please_fill_this_field)
            return false
        } else {
            binding.textTime.error = null
        }
        if (viewModel.isDataNotFilled(binding.textDirectionsEditText.text.toString())) {
            binding.textDirections.error = getString(R.string.please_fill_this_field)
            return false
        } else {
            binding.textDirections.error = null
        }
        return true
    }

}