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
    EVENING("evening")
}

fun getLocaleWeather(locale: String): Int {
    return when (locale) {
        WeatherCondition.CLEAR.condition -> R.string.clear
        WeatherCondition.PARTLY_CLOUDY.condition -> R.string.partly_cloudy
        WeatherCondition.CLOUDY.condition -> R.string.cloudy
        WeatherCondition.OVERCAST.condition -> R.string.overcast
        WeatherCondition.DRIZZLE.condition -> R.string.drizzle
        WeatherCondition.LIGHT_RAIN.condition -> R.string.light_rain
        WeatherCondition.RAIN.condition ->R.string.rain
        WeatherCondition.MODERATE_RAIN.condition ->R.string.moderate_rain
        WeatherCondition.HEAVY_RAIN.condition ->R.string.heavy_rain
        WeatherCondition.CONTINUOUS_HEAVY_RAIN.condition -> R.string.continuous_heavy_rain
        WeatherCondition.SHOWERS.condition -> R.string.showers
        WeatherCondition.WET_SNOW.condition -> R.string.wet_snow
        WeatherCondition.LIGHT_SNOW.condition -> R.string.light_snow
        WeatherCondition.SNOW.condition -> R.string.snow
        WeatherCondition.SNOW_SHOWERS.condition -> R.string.snow_showers
        WeatherCondition.HAIL.condition -> R.string.hail
        WeatherCondition.THUNDERSTORM.condition -> R.string.thunderstorm
        WeatherCondition.THUNDERSTORM_WITH_RAIN.condition ->R.string.thunderstorm_with_rain
        WeatherCondition.THUNDERSTORM_WITH_HAIL.condition ->R.string.thunderstorm_with_hail
        WeatherCondition.NIGHT.condition-> R.string.night
        WeatherCondition.MORNING.condition -> R.string.morning
        WeatherCondition.DAY.condition -> R.string.day
        WeatherCondition.EVENING.condition -> R.string.evening
        else -> {R.string.n_a}
    }

}
