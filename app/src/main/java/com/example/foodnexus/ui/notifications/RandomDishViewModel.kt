package com.example.foodnexus.ui.notifications

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodnexus.model.DishRepository
import com.example.foodnexus.model.RandomDishData
import com.example.foodnexus.utils.network.RandomDishAPIService
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class RandomDishViewModel @Inject constructor(private val repository: DishRepository) :
    ViewModel() {

    private val randomDishApiService = RandomDishAPIService()
    private val compositeDisposable = CompositeDisposable()

    val loadRandomDish = MutableLiveData<Boolean>()
    val randomDishResponse = MutableLiveData<RandomDishData.RandomDish>()
    val randomDishLoadingError = MutableLiveData<Boolean>()

    fun getRandomDishFromAPI() {
        loadRandomDish.value = true

        compositeDisposable.add(
            randomDishApiService.getRandomDish()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<RandomDishData.RandomDish>() {
                    override fun onSuccess(dish: RandomDishData.RandomDish) {
                        loadRandomDish.value = false
                        randomDishResponse.value = dish
                        randomDishLoadingError.value = false
                    }

                    override fun onError(e: Throwable?) {
                        loadRandomDish.value = false
                        randomDishLoadingError.value = true
                        e?.printStackTrace()
                    }

                })
        )
    }

}
