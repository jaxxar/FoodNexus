package com.example.foodnexus.ui.notifications

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.foodnexus.databinding.FragmentRandomDishBinding
import com.example.foodnexus.ui.adapters.InstructionsAdapter
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

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}