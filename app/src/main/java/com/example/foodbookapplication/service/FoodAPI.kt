package com.example.foodbookapplication.service

import com.example.foodbookapplication.model.Food
import io.reactivex.Single
import retrofit2.http.GET

interface FoodAPI {
    // Verileri çekiceksek bu GET işlemi olur. Sunucuya bir veri yollayacaksak bu post işlemi olur

    // https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    // BASE_URL ->  https://raw.githubusercontent.com/
    // atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    fun getFood() : Single<List<Food>>
}