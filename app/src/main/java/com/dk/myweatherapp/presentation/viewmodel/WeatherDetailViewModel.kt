package com.dk.myweatherapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dk.myweatherapp.data.model.City
import com.dk.myweatherapp.data.model.weather_dto.Weather
import com.dk.myweatherapp.data.repository.RepositoryImpl
import com.dk.myweatherapp.domain.WeatherInteractor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherDetailViewModel(
    private var getRequestWeather: MutableLiveData<State> = MutableLiveData()
) : ViewModel() {

    private val repository = RepositoryImpl()
    private val weatherInteractor = WeatherInteractor(repository)

    fun getWeatherState() = getRequestWeather

    fun getWeatherRequestState(city: City) {
        getRequestWeather.value = State.Loading

        weatherInteractor.getDetail(city, object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                val detailWeather: Weather? = response.body()
                getRequestWeather.postValue(
                    if (response.isSuccessful && detailWeather != null) {
                        State.SuccessWeather(detailWeather)
                    } else {
                        State.Error(Throwable("Ошибка!"))
                    }
                )
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                getRequestWeather.postValue(State.Error(Throwable(t.message ?: "Ошибка запроса")))
            }

        })
    }
}