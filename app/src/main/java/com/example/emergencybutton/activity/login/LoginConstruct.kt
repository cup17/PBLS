package com.example.emergencybutton.activity.forpass

import com.example.emergencybutton.model.UserItem
import com.example.emergencybutton.model.UserResponse
import retrofit2.Response

interface LoginConstruct {
    interface View {
        fun goToHome()
        fun goToRegister()
        fun goToForpass()
        fun saveUserData(data: Response<UserItem>?)
        fun isFailure(msg : String)
        fun isSuccess(msg : String)
        fun checkLogin()
    }

    interface Presenter {
        fun pushLoginData(email : String, pass : String)
    }
}