package com.example.foodnexus.ui.notifications

import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_COMPACT
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.foodnexus.R
import com.example.foodnexus.databinding.FragmentRandomDishBinding
import com.example.foodnexus.model.DishesData
import com.example.foodnexus.model.RandomDishData
import com.example.foodnexus.ui.adapters.InstructionsAdapter
import com.example.foodnexus.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RandomDishFragment : Fragment() {

    private val randomDishViewModel: RandomDishViewModel by viewModels()
    private var binding: FragmentRandomDishBinding? = null
    private var randomDish: RandomDishData.RandomDish? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save_dish_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_save_dish -> {
                if (randomDish != null) {
                    randomDishViewModel.insertDish(createDishData(randomDish!!))
                } else {
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.save_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun randomDishViewModelObserver() {
        randomDishViewModel.randomDishResponse.observe(
            viewLifecycleOwner,
            { randomDishResponse ->
                randomDishResponse?.let {
                    randomDish = randomDishResponse
                    binding.apply {
                        this!!.textTitle.text = randomDishResponse.recipes[0].title
                        val circularProgressDrawable = CircularProgressDrawable(requireContext())
                        circularProgressDrawable.strokeWidth = 5f
                        circularProgressDrawable.centerRadius = 30f
                        circularProgressDrawable.start()
                        Glide.with(this@RandomDishFragment)
                            .load(randomDishResponse.recipes[0].image)
                            .placeholder(circularProgressDrawable)
                            .into(image)
                        textSummary.text = Html.fromHtml(
                            randomDishResponse.recipes[0].summary,
                            Html.FROM_HTML_MODE_COMPACT
                        )
                        cheapCheckbox.isChecked = randomDishResponse.recipes[0].cheap
                        veganCheckbox.isChecked = randomDishResponse.recipes[0].vegan
                        vegetarianCheckbox.isChecked = randomDishResponse.recipes[0].vegetarian
                        healthyCheckbox.isChecked = randomDishResponse.recipes[0].veryHealthy
                        prepTime.text = resources.getString(
                            R.string.cook_time_title,
                            randomDishResponse.recipes[0].readyInMinutes.toString()
                        )
                        servings.text = resources.getString(
                            R.string.servings_title,
                            randomDishResponse.recipes[0].servings.toString()
                        )

                        val adapter = InstructionsAdapter(
                            requireContext(),
                            randomDishResponse.recipes[0].analyzedInstructions[0].steps
                        )
                        val layoutManager =
                            LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
                        binding?.instructionsRecycler?.layoutManager = layoutManager
                        binding?.instructionsRecycler?.adapter = adapter
                    }
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

    private fun createDishData(dish: RandomDishData.RandomDish): DishesData {
        var ingredients = ""
        for (value in dish.recipes[0].extendedIngredients) {
            ingredients = if (ingredients.isEmpty()) {
                value.original
            } else {
                ingredients + ", \n" + value.original
            }
        }
        return DishesData(
            dish.recipes[0].image,
            Constants.DISH_IMAGE_SOURCE_ONLINE,
            dish.recipes[0].title,
            resources.getString(R.string.chip_easy),
            dish.recipes[0].dishTypes[0],
            ingredients,
            dish.recipes[0].readyInMinutes.toString(),
            Html.fromHtml(dish.recipes[0].instructions, FROM_HTML_MODE_COMPACT).toString(),
            false
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}