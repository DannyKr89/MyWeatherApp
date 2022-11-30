package com.dk.myweatherapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dk.myweatherapp.App.Companion.getHistoryWeatherDB
import com.dk.myweatherapp.data.model.City
import com.dk.myweatherapp.data.model.weather_dto.Weather
import com.dk.myweatherapp.data.repository.GetDetailRepositoryImpl
import com.dk.myweatherapp.data.repository.LocalRepositoryImpl
import com.dk.myweatherapp.domain.GetDetailInteractor
import com.dk.myweatherapp.domain.GetDetailRepository
import com.dk.myweatherapp.domain.GetLocalDBInteractor
import com.dk.myweatherapp.domain.GetLocalDBRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherDetailViewModel(
    private var getRequestWeather: MutableLiveData<State> = MutableLiveData(),
    private val repository: GetDetailRepository = GetDetailRepositoryImpl(),
    private val localRepository: GetLocalDBRepository = LocalRepositoryImpl(getHistoryWeatherDB()),
    private val getDetailInteractor: GetDetailInteractor = GetDetailInteractor(repository),
    private val getLocalDBInteractor: GetLocalDBInteractor = GetLocalDBInteractor(localRepository)
) : ViewModel() {

    private fun saveWeatherToDB(weather: Weather) {
        getLocalDBInteractor.saveToDB(weather)
    }


    fun getWeatherState() = getRequestWeather

    fun getWeatherRequestState(city: City) {
        getRequestWeather.value = State.Loading

        getDetailInteractor.getDetail(city, object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                val detailWeather: Weather? = response.body().apply {
                    this?.city = city
                }
                if (detailWeather != null) {
                    getRequestWeather.postValue(State.SuccessWeather(detailWeather))
                    Thread{
                        saveWeatherToDB(detailWeather)
                    }.start()
                } else {
                    State.Error(Throwable("Ошибка!"))
                }

            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                getRequestWeather.postValue(State.Error(Throwable(t.message ?: "Ошибка запроса")))
            }

        })
    }
}