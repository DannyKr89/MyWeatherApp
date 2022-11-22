package com.dk.myweatherapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dk.myweatherapp.App.Companion.getHistoryWeatherDB
import com.dk.myweatherapp.data.model.City
import com.dk.myweatherapp.data.model.weather_dto.Weather
import com.dk.myweatherapp.data.repository.LocalRepositoryImpl
import com.dk.myweatherapp.data.repository.RepositoryImpl
import com.dk.myweatherapp.domain.LocalRepository
import com.dk.myweatherapp.domain.WeatherInteractor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherDetailViewModel(
    private var getRequestWeather: MutableLiveData<State> = MutableLiveData(),
    private val repository: RepositoryImpl = RepositoryImpl(),
    private val weatherInteractor: WeatherInteractor = WeatherInteractor(repository),
    private val localRepository: LocalRepository = LocalRepositoryImpl(getHistoryWeatherDB())
) : ViewModel() {

    private fun saveWeatherToDB(weather: Weather) {
        localRepository.saveToDB(weather)
    }


    fun getWeatherState() = getRequestWeather

    fun getWeatherRequestState(city: City) {
        getRequestWeather.value = State.Loading

        weatherInteractor.getDetail(city, object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                val detailWeather: Weather? = response.body().apply {
                    this?.city = city
                }
                if (response.isSuccessful && detailWeather != null) {
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