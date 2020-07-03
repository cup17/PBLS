package com.example.emergencybutton.activity.maps

import android.location.Location

interface MapsConstruct {
    interface View {
        fun buildLocationRequest()
        fun buildLocationCallback()
        fun onError(msg: String)
        fun onSuccess(msg: String)
        fun showDexterPermission()
    }

    interface Presenter {
        fun getAllEmergencies(lastLocation: Location)
    }
}