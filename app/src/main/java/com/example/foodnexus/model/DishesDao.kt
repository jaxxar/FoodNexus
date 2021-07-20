package com.example.foodnexus.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DishesDao {

    @Query("SELECT * FROM dishes_table ORDER BY id")
    fun getAllDishes(): Flow<List<DishesData>>

    @Query("SELECT * FROM dishes_table WHERE favourite_dish = 1")
    fun getFavoriteDishes(): Flow<List<DishesData>>

    @Query("SELECT * FROM dishes_table WHERE cooking_time <= 60")
    fun getQuickDishes(): Flow<List<DishesData>>

    @Query("SELECT * FROM dishes_table WHERE difficulty = 'Easy'")
    fun getEasyDishes(): Flow<List<DishesData>>

    @Query("SELECT * FROM dishes_table WHERE difficulty = 'Medium'")
    fun getMediumDishes(): Flow<List<DishesData>>

    @Query("SELECT * FROM dishes_table WHERE difficulty = 'Hard'")
    fun getHardDishes(): Flow<List<DishesData>>

    @Query("SELECT * FROM dishes_table WHERE id = :selectedId")
    fun getDishFromId(selectedId: String): Flow<DishesData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dishesData: DishesData)

    @Update
    suspend fun updateDish(dish: DishesData)

    @Delete
    suspend fun deleteDish(selectedId: DishesData)

}