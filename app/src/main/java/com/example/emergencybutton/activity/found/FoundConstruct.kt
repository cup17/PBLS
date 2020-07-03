package com.example.emergencybutton.activity.found

import okhttp3.MultipartBody
import okhttp3.RequestBody

interface FoundConstruct {
    interface View{
        fun onSuccess(msg: String)
        fun onFailure(msg: String)
        fun goToHome()
    }
    interface Presenter{
        fun pushDataFound(roleType: RequestBody, name: RequestBody, postersName: RequestBody, number: RequestBody, date: RequestBody, location: RequestBody, description: RequestBody, image: MultipartBody.Part)
    }
}