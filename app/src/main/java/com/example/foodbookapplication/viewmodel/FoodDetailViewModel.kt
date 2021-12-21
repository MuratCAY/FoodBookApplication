package com.example.foodbookapplication.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.foodbookapplication.base.BaseViewModel
import com.example.foodbookapplication.data.db.FoodDatabase
import com.example.foodbookapplication.model.Food
import kotlinx.coroutines.launch

class FoodDetailViewModel(application: Application) : BaseViewModel(application) {
    val foodLiveData = MutableLiveData<Food>()

    fun takeRoomData(uuid: Int) {
        launch {
            val dao = FoodDatabase(getApplication()).foodDao()
            val food = dao.getFood(uuid)
            foodLiveData.value = food
        }
    }
}