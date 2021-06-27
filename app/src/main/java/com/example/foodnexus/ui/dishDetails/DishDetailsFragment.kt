package com.example.foodnexus.ui.dishDetails

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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
                args.selectedDish.favouriteDish = !args.selectedDish.favouriteDish
                viewModel.favouriteDish(args.selectedDish)
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
            }
            Glide.with(this@DishDetailsFragment)
                .load(args.selectedDish.image)
                .centerCrop()
                .placeholder(circularProgressDrawable)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        resource?.let {
                            Palette.from(it.toBitmap()).generate { palette ->
                                val intColor =
                                    palette?.lightVibrantSwatch?.rgb
                                        ?: palette?.lightMutedSwatch?.rgb
                                        ?: palette?.dominantSwatch?.rgb
                                        ?: 0
                                detailsScrollView.setBackgroundColor(intColor)
                            }
                        }
                        return false
                    }

                })
                .into(image)
            textTitleEditText.setText(args.selectedDish.title)
            textDifficultyEditText.setText(args.selectedDish.difficulty)
            textCategoryEditText.setText(args.selectedDish.category)
            textIngredientsEditText.setText(args.selectedDish.ingredients)
            textTimeEditText.setText(args.selectedDish.cookingTime)
            textDirectionsEditText.setText(args.selectedDish.directionsToCook)
        }
    }
}