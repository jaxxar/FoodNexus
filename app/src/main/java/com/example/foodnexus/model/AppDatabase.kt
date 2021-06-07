package com.example.foodnexus.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [DishesData::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dishesDao(): DishesDao

    class Callback @Inject constructor(
        private val database: Provider<AppDatabase>
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dishes = database.get().dishesDao()
        }
    }
}