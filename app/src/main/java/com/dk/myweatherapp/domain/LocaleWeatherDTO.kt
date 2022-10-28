package com.dk.myweatherapp.domain

import android.content.res.Resources
import com.dk.myweatherapp.R

enum class LocaleWeatherDTO(val condition: String) {
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
        LocaleWeatherDTO.CLEAR.condition -> R.string.clear
        LocaleWeatherDTO.PARTLY_CLOUDY.condition -> R.string.partly_cloudy
        LocaleWeatherDTO.CLOUDY.condition -> R.string.cloudy
        LocaleWeatherDTO.OVERCAST.condition -> R.string.overcast
        LocaleWeatherDTO.DRIZZLE.condition -> R.string.drizzle
        LocaleWeatherDTO.LIGHT_RAIN.condition -> R.string.light_rain
        LocaleWeatherDTO.RAIN.condition ->R.string.rain
        LocaleWeatherDTO.MODERATE_RAIN.condition ->R.string.moderate_rain
        LocaleWeatherDTO.HEAVY_RAIN.condition ->R.string.heavy_rain
        LocaleWeatherDTO.CONTINUOUS_HEAVY_RAIN.condition -> R.string.continuous_heavy_rain
        LocaleWeatherDTO.SHOWERS.condition -> R.string.showers
        LocaleWeatherDTO.WET_SNOW.condition -> R.string.wet_snow
        LocaleWeatherDTO.LIGHT_SNOW.condition -> R.string.light_snow
        LocaleWeatherDTO.SNOW.condition -> R.string.snow
        LocaleWeatherDTO.SNOW_SHOWERS.condition -> R.string.snow_showers
        LocaleWeatherDTO.HAIL.condition -> R.string.hail
        LocaleWeatherDTO.THUNDERSTORM.condition -> R.string.thunderstorm
        LocaleWeatherDTO.THUNDERSTORM_WITH_RAIN.condition ->R.string.thunderstorm_with_rain
        LocaleWeatherDTO.THUNDERSTORM_WITH_HAIL.condition ->R.string.thunderstorm_with_hail
        LocaleWeatherDTO.NIGHT.condition-> R.string.night
        LocaleWeatherDTO.MORNING.condition -> R.string.morning
        LocaleWeatherDTO.DAY.condition -> R.string.day
        LocaleWeatherDTO.EVENING.condition -> R.string.evening
        else -> {R.string.n_a}
    }

}
