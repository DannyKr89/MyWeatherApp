package com.dk.myweatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dk.myweatherapp.domain.RepositoryImpl
import com.dk.myweatherapp.domain.WeatherApi
import com.dk.myweatherapp.model.CitiesLocation
import com.dk.myweatherapp.model.City
import com.dk.myweatherapp.model.getRussianCities
import com.dk.myweatherapp.model.weather_dto.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel(
    private var getNextLoc: MutableLiveData<Boolean> = MutableLiveData(),
    private var getRequestWeatherList: MutableLiveData<State> = MutableLiveData(),
    private var getRequestWeather: MutableLiveData<State> = MutableLiveData()
) : ViewModel() {
    init {
        getNextLocation().value = true
        getRequestWeatherList.value = State.SuccessWeatherList(getRussianCities())
        getRequestWeather.value = State.Loading
    }

    private val repository = RepositoryImpl(WeatherApi())

    private val callback = object : Callback<Weather> {
        override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
            val serverResponse: Weather? = response.body()
            getRequestWeather.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    State.Error(Throwable("Ошибка!"))
                }
            )
        }

        override fun onFailure(call: Call<Weather>, t: Throwable) {
            getRequestWeather.postValue(State.Error(Throwable(t.message ?: "Ошибка запроса")))
        }

    }

    private fun checkResponse(serverResponse: Weather): State {
        return State.SuccessWeather(serverResponse)
    }


    fun getNextLocation(): MutableLiveData<Boolean> {
        return getNextLoc
    }


    fun changeLocation(nextLoc: Boolean) {
        getNextLoc.value = repository.getNextLoc(nextLoc)
    }


    fun getWeatherListState(): MutableLiveData<State>{
        return getRequestWeatherList
    }

    fun getWeatherState():MutableLiveData<State>{
        return getRequestWeather
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

    fun getWeatherRequestState(city: City){
        getRequestWeather.value = State.Loading
        repository.getWeather(city, callback)
    }

    fun getRussiansCitiesList() {
        getWeatherListRequestState(CitiesLocation.RussianCities)
    }

    fun getWorldCitiesList() {
        getWeatherListRequestState(CitiesLocation.WorldCities)
    }

}
