package com.dk.myweatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dk.myweatherapp.domain.RepositoryImpl
import com.dk.myweatherapp.model.CitiesLocation
import com.dk.myweatherapp.model.getRussianCities
import java.lang.Thread.sleep

class WeatherViewModel(
    private var getNextLoc: MutableLiveData<Boolean> = MutableLiveData(),
    private var getRequest: MutableLiveData<State> = MutableLiveData()
) : ViewModel() {
    init {
        getNextLocation().value = true
        getRequest.value = State.Success(getRussianCities())
    }

    private val repository = RepositoryImpl()


    fun getNextLocation():MutableLiveData<Boolean>{
        return getNextLoc
    }


    fun changeLocation(nextLoc: Boolean){
        getNextLoc.value = repository.getNextLoc(nextLoc)
    }


    fun getWeatherState(): MutableLiveData<State>{
        return getRequest
    }

    private fun getRequestState(location: CitiesLocation){
        getRequest.value = State.Loading
        Thread {
            if (false) {
                getRequest.postValue(State.Error(Throwable("Ошибка загрузки")))
            }else{
                getRequest.postValue(State.Success(repository.getWeatherList(location)))
            }
        }.start()
    }

    fun getRussiansCitiesList() {
        getRequestState(CitiesLocation.RussianCities)
    }

    fun getWorldCitiesList() {
        getRequestState(CitiesLocation.WorldCities)
    }

}
