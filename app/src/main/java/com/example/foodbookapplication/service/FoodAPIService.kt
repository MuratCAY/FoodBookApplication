package com.example.foodbookapplication.service

import com.example.foodbookapplication.model.Food
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class FoodAPIService {
    // Verileri çekiceksek bu GET işlemi olur. Sunucuya bir veri yollayacaksak bu post işlemi olur

    // https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    // BASE_URL ->  https://raw.githubusercontent.com/
    // atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // rxjava koyduğumuz için bu satır var
        .build()
        .create(FoodAPI::class.java)

    fun getData(): Single<List<Food>> {
        return api.getFood()
    }
}