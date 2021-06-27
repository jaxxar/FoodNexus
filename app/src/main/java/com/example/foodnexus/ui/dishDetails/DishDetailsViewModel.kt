package com.example.foodnexus.ui.dishDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodnexus.model.DishRepository
import com.example.foodnexus.model.DishesData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DishDetailsViewModel @Inject constructor(private val repository: DishRepository) :
    ViewModel() {

    fun favouriteDish(dish: DishesData) = viewModelScope.launch {
        repository.updateDishData(dish)
    }

}