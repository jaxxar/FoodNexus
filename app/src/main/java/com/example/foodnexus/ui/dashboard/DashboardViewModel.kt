package com.example.foodnexus.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.foodnexus.model.DishRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(repository: DishRepository) : ViewModel() {

    val favouriteDishes = repository.favouriteDishes.asLiveData()
    val quickDishes = repository.quickDishes.asLiveData()
    val easyDishes = repository.easyDishes.asLiveData()
    val mediumDishes = repository.mediumDishes.asLiveData()
    val hardDishes = repository.hardDishes.asLiveData()

}