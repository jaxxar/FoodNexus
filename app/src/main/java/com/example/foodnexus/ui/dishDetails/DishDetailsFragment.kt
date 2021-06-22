package com.example.foodnexus.ui.dishDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.foodnexus.R
import com.example.foodnexus.databinding.FragmentDishDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DishDetailsFragment : Fragment() {

    private lateinit var binding: FragmentDishDetailsBinding
    private val args: DishDetailsFragmentArgs by navArgs()
    private val viewModel: DishDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDishDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val circularProgressDrawable = CircularProgressDrawable(requireContext())
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        binding.apply {
            if (args.selectedDish.favouriteDish) {
                Glide.with(this@DishDetailsFragment)
                    .load(R.drawable.ic_favorite)
                    .placeholder(circularProgressDrawable)
                    .into(favouriteImage)
            } else {
                Glide.with(this@DishDetailsFragment)
                    .load(R.drawable.ic_favorite_border)
                    .placeholder(circularProgressDrawable)
                    .into(favouriteImage)
            }
            favouriteImage.setOnClickListener {
                if (args.selectedDish.favouriteDish) {
                    args.selectedDish.favouriteDish = false
                    viewModel.favouriteDish(args.selectedDish)
                    Glide.with(this@DishDetailsFragment)
                        .load(R.drawable.ic_favorite_border)
                        .placeholder(circularProgressDrawable)
                        .into(favouriteImage)
                } else {
                    args.selectedDish.favouriteDish = true
                    viewModel.favouriteDish(args.selectedDish)
                    Glide.with(this@DishDetailsFragment)
                        .load(R.drawable.ic_favorite)
                        .placeholder(circularProgressDrawable)
                        .into(favouriteImage)
                }
            }
            Glide.with(this@DishDetailsFragment)
                .load(args.selectedDish.image)
                .placeholder(circularProgressDrawable)
                .into(image)
            textTitleEditText.setText(args.selectedDish.title)
            textTypeEditText.setText(args.selectedDish.type)
            textCategoryEditText.setText(args.selectedDish.category)
            textIngredientsEditText.setText(args.selectedDish.ingredients)
            textTimeEditText.setText(args.selectedDish.cookingTime)
            textDirectionsEditText.setText(args.selectedDish.directionsToCook)
        }
    }
}