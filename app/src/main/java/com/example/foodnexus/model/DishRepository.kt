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
    suspend fun deleteDishData(dishesData: DishesData) {
        dishesDao.deleteDish(dishesData)
    }

    @WorkerThread
    suspend fun updateDishData(dishesData: DishesData) {
        dishesDao.updateDish(dishesData)
    }

    val allDishes: Flow<List<DishesData>> = dishesDao.getAllDishes()
    val favouriteDishes: Flow<List<DishesData>> = dishesDao.getFavoriteDishes()
    val quickDishes: Flow<List<DishesData>> = dishesDao.getQuickDishes()
    val easyDishes: Flow<List<DishesData>> = dishesDao.getEasyDishes()
    val mediumDishes: Flow<List<DishesData>> = dishesDao.getMediumDishes()
    val hardDishes: Flow<List<DishesData>> = dishesDao.getHardDishes()
}