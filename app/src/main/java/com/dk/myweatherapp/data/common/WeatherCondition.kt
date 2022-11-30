package com.dk.myweatherapp.data.common


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


