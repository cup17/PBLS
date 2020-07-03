package com.example.emergencybutton.helper

import android.content.Context
import android.content.SharedPreferences
import com.example.emergencybutton.activity.login.LoginActivity
import com.example.emergencybutton.model.UserItem

class MyPref() {

    private val myPref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    companion object {
        private const val PREF_NAME = "userPref"
        private const val PREF_MODE = Context.MODE_PRIVATE
    }

}