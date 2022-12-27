package com.dk.myweatherapp.presentation.viewmodel

import android.location.Location

sealed class LocationState {
    data class Success(val location: Location, val message: Int? = null) : LocationState()
    data class Error(val title: Int, val message: Int) : LocationState()
    object Loading : LocationState()
    object Default : LocationState()
}