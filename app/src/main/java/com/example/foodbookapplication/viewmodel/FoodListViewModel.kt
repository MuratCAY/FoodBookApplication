package com.example.foodbookapplication.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.foodbookapplication.base.BaseViewModel
import com.example.foodbookapplication.data.db.FoodDatabase
import com.example.foodbookapplication.data.db.SpecialSharedPreferences
import com.example.foodbookapplication.model.Food
import com.example.foodbookapplication.service.FoodAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FoodListViewModel(application: Application) : BaseViewModel(application) {

    val foods = MutableLiveData<List<Food>>()
    val foodErrorMessage = MutableLiveData<Boolean>()
    val foodLoadingProgressBar = MutableLiveData<Boolean>()

    private val foodApiService = FoodAPIService()
    private val disposable = CompositeDisposable()
    // disposable bir kere kullan at demek

    private val specialSharedPreferences = SpecialSharedPreferences(getApplication())

    private var updateTime =
        0.2 * 60 * 1000 * 1000 * 1000L // Bu dakika nın nano time a çevrilmiş hali

    fun refreshData() {
        val recordingTime = specialSharedPreferences.getTime()
        if (recordingTime != null && recordingTime != 0L && System.nanoTime() - recordingTime < updateTime) {
            // Sqlite dan çek
            getDataFromSQLite()
        } else {
            getDataFromEthernet()
        }
    }

    fun refreshFromInternet(){
        getDataFromEthernet()
    }

    private fun getDataFromSQLite() {
        foodLoadingProgressBar.value = true

        launch {
            val foodsList = FoodDatabase(getApplication()).foodDao().getAllFood()
            showFoods(foodsList)
            Toast.makeText(getApplication(), "We got the food from the room", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun getDataFromEthernet() {
        foodLoadingProgressBar.value = true

        // IO thread = Input output işlemleri, daha çok veri alışverişinde kullandığımız
        // Default thread = CPU yu yoğun işlemlerde, görsel işlemlerde vs gibi yerlerde kullanırız

        disposable.add(
            foodApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Food>>() {
                    override fun onSuccess(t: List<Food>) {
                        // Başarılı olursa
                        /**
                        foods.value = t
                        foodErrorMessage.value = false
                        foodLoadingProgressBar.value = false
                         */
                        keepSqlite(t)
                        Toast.makeText(getApplication(), "We got the food from the ethernet", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        // Hata Alırsak
                        foodErrorMessage.value = true
                        foodLoadingProgressBar.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun showFoods(foodsList: List<Food>) {
        foods.value = foodsList
        foodErrorMessage.value = false
        foodLoadingProgressBar.value = false
    }

    private fun keepSqlite(foodsList: List<Food>) {
        launch {
            val dao = FoodDatabase(getApplication()).foodDao()
            dao.deleteAllFood()
            val uuidList =
                dao.insertAll(*foodsList.toTypedArray()) // otomatik olarak id ler oluşturuyor
            var i = 0
            while (i < foodsList.size) {
                foodsList[i].uuid = uuidList[i].toInt()
                i += 1
            }
            showFoods(foodsList)
        }
        specialSharedPreferences.saveTime(System.nanoTime())
    }


}