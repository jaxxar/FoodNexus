package com.example.foodnexus.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DishesDao {

    @Query("SELECT * FROM dishes_table ORDER BY id")
    fun getAllDishes(): Flow<List<DishesData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dishesData: DishesData)

}