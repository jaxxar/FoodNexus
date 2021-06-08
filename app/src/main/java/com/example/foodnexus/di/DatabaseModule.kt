package com.example.foodnexus.di

import android.app.Application
import androidx.room.Room
import com.example.foodnexus.model.AppDatabase
import com.example.foodnexus.model.DishRepository
import com.example.foodnexus.model.DishesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        app: Application,
        callback: AppDatabase.Callback
    ) = Room.databaseBuilder(app, AppDatabase::class.java, "dishes_db")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()

    @Provides
    fun provideDishesDao(db: AppDatabase) = db.dishesDao()

    @Provides
    fun provideRepository(dao: DishesDao) = DishRepository(dao)
}