package com.example.emergencybutton.model

data class EmergencyItem (
    var id: String,
    var name: String,
    var msg: String,
    var lat: Double,
    var lng: Double,
    var distanceInKm: Double
)