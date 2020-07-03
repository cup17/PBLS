package com.example.emergencybutton.activity.lost

import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

interface LostConstruct {
    interface View{
        fun onSuccess(msg: String)
        fun onFailure(msg: String)
        fun goToHome()
    }
    interface Presenter{
        fun pushDataLost(roleType: RequestBody, name: RequestBody, postersName: RequestBody, number: RequestBody, date: RequestBody, location: RequestBody, description: RequestBody, image: MultipartBody.Part)
    }
}