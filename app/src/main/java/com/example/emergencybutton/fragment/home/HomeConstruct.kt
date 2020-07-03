package com.example.emergencybutton.fragment.home

import android.app.AlertDialog

interface HomeConstruct {
    interface View{
        fun onFailure(msg : String)
        fun onSuccess(msg : String)
        fun showProfileImage()
//        fun getProfileImage()
        fun goToLost()
        fun goToFound()
        fun showDialogLosFon()
        fun showDialog(): AlertDialog?
        fun buildLocationRequest()
        fun buildLocationCallback()
    }
    interface Presenter{
        fun pushEmergencyON(name: String, lat: String, lng: String)
        fun pushEmergencyOFF(name: String)
    }
}