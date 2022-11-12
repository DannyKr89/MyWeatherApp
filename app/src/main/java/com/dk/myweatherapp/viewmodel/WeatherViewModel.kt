package com.dk.myweatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dk.myweatherapp.domain.RepositoryImpl
import com.dk.myweatherapp.model.CitiesLocation
import com.dk.myweatherapp.model.getRussianCities

class WeatherViewModel(
    private var getNextLoc: MutableLiveData<Boolean> = MutableLiveData(),
    private var getRequestWeatherList: MutableLiveData<State> = MutableLiveData(),
) : ViewModel() {
    init {
        getNextLocation().value = true
        getRequestWeatherList.value = State.SuccessWeatherList(getRussianCities())
    }

    private val repository = RepositoryImpl()


    fun getNextLocation():MutableLiveData<Boolean>{
        return getNextLoc
    }


    fun changeLocation(nextLoc: Boolean){
        getNextLoc.value = repository.getNextLoc(nextLoc)
    }


    fun getWeatherListState(): MutableLiveData<State>{
        return getRequestWeatherList
    }


    private fun getWeatherListRequestState(location: CitiesLocation){
        getRequestWeatherList.value = State.Loading
        Thread {
            if (false) {
                getRequestWeatherList.postValue(State.Error(Throwable("Ошибка загрузки")))
            }else{
                getRequestWeatherList.postValue(State.SuccessWeatherList(repository.getWeatherList(location)))
            }
        }.start()
    }

    fun getRussiansCitiesList() {
        getWeatherListRequestState(CitiesLocation.RussianCities)
    }

    fun getWorldCitiesList() {
        getWeatherListRequestState(CitiesLocation.WorldCities)
    }

}
