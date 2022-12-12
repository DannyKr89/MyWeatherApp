package com.dk.myweatherapp.presentation.viewmodel

import android.location.Location

sealed class AddressState {
    data class Success(val title: String, val location: Location) : AddressState()
    data class Error(val title: Int, val message: Int): AddressState()
    object Loading: AddressState()
    object Default: AddressState()
}