package com.example.foodnexus.ui.notifications

import androidx.lifecycle.ViewModel
import com.example.foodnexus.model.DishRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RandomDishViewModel @Inject constructor(private val repository: DishRepository) : ViewModel()