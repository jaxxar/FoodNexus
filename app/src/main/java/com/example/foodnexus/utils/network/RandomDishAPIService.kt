package com.example.foodnexus.utils.network

import com.example.foodnexus.model.RandomDishData
import com.example.foodnexus.utils.Constants
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RandomDishAPIService {

    private val api = Retrofit.Builder().baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(RandomDishAPI::class.java)

    fun getRandomDish(): Single<RandomDishData.Recipe> {
        return api.getRandomDish(
            Constants.API_KEY_VALUE,
            Constants.LIMIT_LICENCE_VALUE,
            Constants.NUMBER_VALUE
        )
    }
}