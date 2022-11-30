package com.dk.myweatherapp.data.common

import com.dk.myweatherapp.R
import com.dk.myweatherapp.data.ICON_URL
import com.dk.myweatherapp.data.model.weather_dto.Weather
import com.dk.myweatherapp.data.room.HistoryWeather

fun translateWeatherCondition(weatherCondition: WeatherCondition): Int {
    return when (weatherCondition) {
        WeatherCondition.CLEAR -> R.string.clear
        WeatherCondition.PARTLY_CLOUDY -> R.string.partly_cloudy
        WeatherCondition.CLOUDY -> R.string.cloudy
        WeatherCondition.OVERCAST -> R.string.overcast
        WeatherCondition.DRIZZLE -> R.string.drizzle
        WeatherCondition.LIGHT_RAIN -> R.string.light_rain
        WeatherCondition.RAIN -> R.string.rain
        WeatherCondition.MODERATE_RAIN -> R.string.moderate_rain
        WeatherCondition.HEAVY_RAIN -> R.string.heavy_rain
        WeatherCondition.CONTINUOUS_HEAVY_RAIN -> R.string.continuous_heavy_rain
        WeatherCondition.SHOWERS -> R.string.showers
        WeatherCondition.WET_SNOW -> R.string.wet_snow
        WeatherCondition.LIGHT_SNOW -> R.string.light_snow
        WeatherCondition.SNOW -> R.string.snow
        WeatherCondition.SNOW_SHOWERS -> R.string.snow_showers
        WeatherCondition.HAIL -> R.string.hail
        WeatherCondition.THUNDERSTORM -> R.string.thunderstorm
        WeatherCondition.THUNDERSTORM_WITH_RAIN -> R.string.thunderstorm_with_rain
        WeatherCondition.THUNDERSTORM_WITH_HAIL -> R.string.thunderstorm_with_hail
        WeatherCondition.NIGHT -> R.string.night
        WeatherCondition.MORNING -> R.string.morning
        WeatherCondition.DAY -> R.string.day
        WeatherCondition.EVENING -> R.string.evening
    }
}

fun convertWeatherToHistoryWeather(weather: Weather): HistoryWeather {
    return HistoryWeather(
        0,
        weather.city.name,
        weather.fact.temp,
        weather.fact.condition,
        ICON_URL + weather.fact.icon + ".svg"
    )
}