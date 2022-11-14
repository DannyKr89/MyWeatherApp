package com.dk.myweatherapp.domain

import com.dk.myweatherapp.R

enum class WeatherCondition(val condition: String) {
    CLEAR("clear"),
    PARTLY_CLOUDY("partly-cloudy"),
    CLOUDY("cloudy"),
    OVERCAST("overcast"),
    DRIZZLE("drizzle"),
    LIGHT_RAIN("light-rain"),
    RAIN("rain"),
    MODERATE_RAIN("moderate-rain"),
    HEAVY_RAIN("heavy-rain"),
    CONTINUOUS_HEAVY_RAIN("continuous-heavy-rain"),
    SHOWERS("showers"),
    WET_SNOW("wet-snow"),
    LIGHT_SNOW("light-snow"),
    SNOW("snow"),
    SNOW_SHOWERS("snow-showers"),
    HAIL("hail"),
    THUNDERSTORM("thunderstorm"),
    THUNDERSTORM_WITH_RAIN("thunderstorm-with-rain"),
    THUNDERSTORM_WITH_HAIL("thunderstorm-with-hail"),
    NIGHT("night"),
    MORNING("morning"),
    DAY("day"),
    EVENING("evening");

    companion object {
        fun getWeatherCondition(condition: String): WeatherCondition {
            return values().find { condition == it.condition }!!
        }
    }
}

fun translateWeatherCondition(weatherCondition: WeatherCondition): Int {
    return when (weatherCondition) {
        WeatherCondition.CLEAR -> R.string.clear
        WeatherCondition.PARTLY_CLOUDY -> R.string.partly_cloudy
        WeatherCondition.CLOUDY -> R.string.cloudy
        WeatherCondition.OVERCAST -> R.string.overcast
        WeatherCondition.DRIZZLE -> R.string.drizzle
        WeatherCondition.LIGHT_RAIN -> R.string.light_rain
        WeatherCondition.RAIN ->R.string.rain
        WeatherCondition.MODERATE_RAIN ->R.string.moderate_rain
        WeatherCondition.HEAVY_RAIN ->R.string.heavy_rain
        WeatherCondition.CONTINUOUS_HEAVY_RAIN -> R.string.continuous_heavy_rain
        WeatherCondition.SHOWERS -> R.string.showers
        WeatherCondition.WET_SNOW -> R.string.wet_snow
        WeatherCondition.LIGHT_SNOW -> R.string.light_snow
        WeatherCondition.SNOW -> R.string.snow
        WeatherCondition.SNOW_SHOWERS -> R.string.snow_showers
        WeatherCondition.HAIL -> R.string.hail
        WeatherCondition.THUNDERSTORM -> R.string.thunderstorm
        WeatherCondition.THUNDERSTORM_WITH_RAIN ->R.string.thunderstorm_with_rain
        WeatherCondition.THUNDERSTORM_WITH_HAIL ->R.string.thunderstorm_with_hail
        WeatherCondition.NIGHT-> R.string.night
        WeatherCondition.MORNING -> R.string.morning
        WeatherCondition.DAY -> R.string.day
        WeatherCondition.EVENING -> R.string.evening
    }

}
