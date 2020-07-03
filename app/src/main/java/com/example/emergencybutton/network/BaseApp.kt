package com.example.emergencybutton.network

import android.app.Application
import androidx.multidex.MultiDex
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interceptors.HttpLoggingInterceptor

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(applicationContext)
        AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY)
    }

}