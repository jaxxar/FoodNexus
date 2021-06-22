package com.example.foodnexus.model

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DishRepository @Inject constructor(private val dishesDao: DishesDao) {

    @WorkerThread
    suspend fun insertDishData(dishesData: DishesData) {
        dishesDao.insert(dishesData)
    }

    @WorkerThread
    suspend fun deleteDishData(id: Int) {
        dishesDao.deleteAllQuestions(id)
    }

    val allDishes: Flow<List<DishesData>> = dishesDao.getAllDishes()
}