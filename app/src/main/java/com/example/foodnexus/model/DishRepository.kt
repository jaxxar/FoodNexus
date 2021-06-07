package com.example.foodnexus.model

import androidx.annotation.WorkerThread
import javax.inject.Inject

class DishRepository @Inject constructor(private val dishesDao: DishesDao) {

    @WorkerThread
    suspend fun insertDishData(dishesData: DishesData) {
        dishesDao.insert(dishesData)
    }
}