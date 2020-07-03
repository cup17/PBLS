package com.example.emergencybutton.network

import com.example.emergencybutton.BuildConfig

object UtilsApi {
    // Mendeklarasikan Interface BaseApiService
    fun getAPIService(): BaseApiService? {
        return RetrofitClient.getClient(BuildConfig.BASE_URL)?.create(BaseApiService::class.java)
    }
}