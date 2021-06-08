package com.example.foodnexus.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.foodnexus.model.DishRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(repository: DishRepository) : ViewModel() {

    val allDishes = repository.allDishes.asLiveData()

}