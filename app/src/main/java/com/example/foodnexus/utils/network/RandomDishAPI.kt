package com.example.foodnexus.utils.network

import com.example.foodnexus.model.RandomDishData
import com.example.foodnexus.utils.Constants
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomDishAPI {

    @GET(Constants.API_ENDPOINT_RANDOM)
    fun getRandomDish(
        @Query(Constants.API_KEY) apiKey: String,
        @Query(Constants.LIMIT_LICENCE) limitLicence: Boolean,
        @Query(Constants.NUMBER) number: Int
    ): Single<RandomDishData.Recipe>
}