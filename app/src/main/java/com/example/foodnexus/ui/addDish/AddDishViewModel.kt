package com.example.foodnexus.ui.addDish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodnexus.model.DishRepository
import com.example.foodnexus.model.DishesData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddDishViewModel @Inject constructor(private val repository: DishRepository) : ViewModel() {

    fun isDataNotFilled(text: String): Boolean {
        return text.isBlank()
    }

    fun insertDish(dish: DishesData) = viewModelScope.launch {
        repository.insertDishData(dish)
    }

    fun deleteDish(id: Int) = viewModelScope.launch {
        repository.deleteDishData(id)
    }
}