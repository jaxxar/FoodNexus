package com.example.foodnexus.ui.addDish

import androidx.lifecycle.ViewModel
import com.example.foodnexus.model.DishesDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddDishViewModel @Inject constructor(dishesDao: DishesDao) : ViewModel() {

    fun isDataNotFilled(text: String): Boolean {
        return text.isBlank()
    }
}