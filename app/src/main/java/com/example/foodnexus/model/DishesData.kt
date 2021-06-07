package com.example.foodnexus.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dishes_table")
data class DishesData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "image_source") val imageSource: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "ingredients") val ingredients: String,
    @ColumnInfo(name = "cooking_time") val cookingTime: String,
    @ColumnInfo(name = "instructions") val directionsToCook: String,
    @ColumnInfo(name = "favourite_dish") var favouriteDish: Boolean = false
)