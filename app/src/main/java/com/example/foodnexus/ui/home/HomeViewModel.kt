package com.example.foodnexus.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodnexus.model.DishRepository
import com.example.foodnexus.model.DishesData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: DishRepository) : ViewModel() {

    val allDishes = repository.allDishes.asLiveData()

    fun deleteDish(dish: DishesData) = viewModelScope.launch {
        repository.deleteDishData(dish)
    }

}